package edu.bistu.copyright.protect.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.bistu.copyright.protect.common.HashType;
import edu.bistu.copyright.protect.common.ServiceException;
import edu.bistu.copyright.protect.contract.CopyrightRepository;
import edu.bistu.copyright.protect.dto.CopyrightPageQuery;
import edu.bistu.copyright.protect.dto.CopyrightSaveDTO;
import edu.bistu.copyright.protect.entity.Copyright;
import edu.bistu.copyright.protect.entity.User;
import edu.bistu.copyright.protect.mapper.CopyrightMapper;
import edu.bistu.copyright.protect.service.ICopyrightService;
import edu.bistu.copyright.protect.service.IUserService;
import edu.bistu.copyright.protect.util.HashUtil;
import edu.bistu.copyright.protect.util.WatermarkUtil;
import edu.bistu.copyright.protect.vo.CopyrightVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bytedeco.opencv.opencv_core.Mat;
import org.fisco.bcos.sdk.v3.client.Client;
import org.fisco.bcos.sdk.v3.client.exceptions.ClientException;
import org.fisco.bcos.sdk.v3.client.protocol.response.BcosTransactionReceipt;
import org.fisco.bcos.sdk.v3.codec.datatypes.generated.tuples.generated.Tuple4;
import org.fisco.bcos.sdk.v3.crypto.CryptoSuite;
import org.fisco.bcos.sdk.v3.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.v3.model.CryptoType;
import org.fisco.bcos.sdk.v3.model.TransactionReceipt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Chanvo
 * @since 2023-05-08
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class CopyrightServiceImpl extends ServiceImpl<CopyrightMapper, Copyright> implements ICopyrightService {

    @Value("${upload.path}")
    private String filePath;

    private final IUserService userService;
    private final Client client;

    private CryptoKeyPair getKeypair(String privateKey) {
        CryptoSuite cryptoSuite = new CryptoSuite(CryptoType.ECDSA_TYPE);
        return cryptoSuite.loadKeyPair(privateKey);
    }

    @Override
    public IPage<Copyright> page(Integer uid, Integer index, Integer limit, CopyrightPageQuery query) {
        IPage<Copyright> page = new Page<>(index, limit);
        if (query == null) {
            return page(page);
        }
        LambdaQueryWrapper<Copyright> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(uid != null, Copyright::getUserId, uid)
                .and(StringUtils.hasLength(query.getTitle())
                                || StringUtils.hasLength(query.getContent())
                                || StringUtils.hasLength(query.getTxHash())
                        , w -> w.like(StringUtils.hasLength(query.getContent()),
                                        Copyright::getContent, query.getContent())
                                .like(StringUtils.hasLength(query.getTitle()),
                                        Copyright::getTitle, query.getTitle())
                                .eq(StringUtils.hasLength(query.getTxHash()),
                                        Copyright::getTxHash, query.getTxHash()))
                .orderBy(true, query.getCreateTimeAsc(), Copyright::getCreateTime);
        return page(page, wrapper);
    }

    @Transactional
    @Override
    public Copyright create(Integer uid, CopyrightSaveDTO input) {
        Path path = Path.of(filePath, input.getUrl());
        String watermarkImgName = UUID.randomUUID() + ".png";
        Path watermarkPath = Path.of(filePath, watermarkImgName);
        try {
            WatermarkUtil.addImageWatermarkWithText(path, input.getContent(), watermarkPath);
        } catch (IOException e) {
            throw new ServiceException("水印图像构造失败", e);
        }
        if (!Files.exists(path)) {
            throw new ServiceException("文件不存在");
        }
        // 数据库存储
        Copyright copyright = Copyright
                .builder()
                .userId(uid)
                .content(input.getContent())
                .title(input.getTitle())
                .originFileUrl(input.getUrl())
                .watermarkFileUrl(watermarkImgName)
                .build();
        if (!this.save(copyright)) {
            throw new ServiceException("保存异常");
        }
        List<String> hashes;
        try {
            byte[] fileBytes = HashUtil.readFileBytes(path);
            hashes = List.of(HashUtil.bytes2HashHex(fileBytes, HashType.MD5),
                    HashUtil.bytes2HashHex(fileBytes, HashType.SHA1),
                    HashUtil.bytes2HashHex(fileBytes, HashType.SHA256));
        } catch (IOException e) {
            throw new ServiceException("文件哈希计算失败", e);
        }
        User user = userService.getById(uid);
        // 区块链存储
        CopyrightRepository repository = CopyrightRepository.load(user.getContractChainAddress(), client, getKeypair(user.getPrivateKey()));
        TransactionReceipt receipt = repository.requestStore(
                BigInteger.valueOf(copyright.getId()),
                copyright.getContent(),
                hashes,
                BigInteger.valueOf(Instant.now(Clock.system(ZoneId.of("Asia/Shanghai"))).getEpochSecond()));
        String txHash = receipt.getTransactionHash();
        log.info("Copyright Saved In Blockchain: {}", txHash);
        copyright.setTxHash(txHash);
        updateById(copyright);
        return copyright;
    }

    @Override
    public Boolean del(Integer uid, Integer id) {
        return remove(new LambdaQueryWrapper<Copyright>()
                .eq(Copyright::getUserId, uid)
                .eq(Copyright::getId, id));
    }

    @Override
    public CopyrightVO getByTxHash(Integer uid, String txHash) {
        List<Copyright> copyrights = list(new LambdaQueryWrapper<Copyright>()
                .eq(uid != null, Copyright::getUserId, uid)
                .eq(Copyright::getTxHash, txHash)
                .last("limit 1"));
        if (copyrights == null || copyrights.size() == 0) {
            throw new ServiceException("对象不存在");
        }
        Copyright copyright = copyrights.get(0);
        BcosTransactionReceipt clientReceipt;
        try {
            clientReceipt = client.getTransactionReceipt(txHash, false);
        } catch (ClientException e) {
            throw new ServiceException("查询失败，该存证不存在!", e);
        }
        TransactionReceipt txReceipt = clientReceipt.getTransactionReceipt();
        Tuple4<BigInteger, String, List<String>, BigInteger> input = CopyrightRepository.getRequestStoreInput(txReceipt);
        return CopyrightVO.builder()
                .id(copyright.getId())
                .userId(copyright.getUserId())
                .originFileUrl(copyright.getOriginFileUrl())
                .watermarkFileUrl(copyright.getWatermarkFileUrl())
                .title(copyright.getTitle())
                .content(input.getValue2())
                .md5Hash(input.getValue3().get(0))
                .sha1Hash(input.getValue3().get(1))
                .sha256Hash(input.getValue3().get(2))
                .txHash(txHash)
                .blockNum(txReceipt.getBlockNumber())
                .createTime(copyright.getCreateTime())
                .chainCreateTime(LocalDateTime.ofInstant(
                        Instant.ofEpochSecond(input.getValue4().longValue()),
                        ZoneId.of("Asia/Shanghai")))
                .build();
    }

    @Override
    public String getWatermarkBase64(String url) {
        Path path = Path.of(filePath, url);
        if (!Files.exists(path)) {
            throw new ServiceException("文件不存在");
        }
        try {
            Mat watermark = WatermarkUtil.getImageWatermarkWithText(path);
            return WatermarkUtil.getBase64(watermark);
        } catch (IOException e) {
            throw new RuntimeException("水印解析失败", e);
        }
    }

}
