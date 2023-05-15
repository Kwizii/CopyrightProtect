package edu.bistu.copyright.protect.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.bistu.copyright.protect.common.HashType;
import edu.bistu.copyright.protect.common.ServiceException;
import edu.bistu.copyright.protect.dto.CopyrightPageQuery;
import edu.bistu.copyright.protect.dto.CopyrightSaveDTO;
import edu.bistu.copyright.protect.entity.Copyright;
import edu.bistu.copyright.protect.entity.User;
import edu.bistu.copyright.protect.mapper.CopyrightMapper;
import edu.bistu.copyright.protect.service.IContractService;
import edu.bistu.copyright.protect.service.ICopyrightService;
import edu.bistu.copyright.protect.service.IUserService;
import edu.bistu.copyright.protect.util.HashUtil;
import lombok.RequiredArgsConstructor;
import org.fisco.bcos.sdk.v3.transaction.model.exception.ContractException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Chanvo
 * @since 2023-05-08
 */
@RequiredArgsConstructor
@Service
public class CopyrightServiceImpl extends ServiceImpl<CopyrightMapper, Copyright> implements ICopyrightService {

    @Value("${upload.path}")
    private String filePath;

    private final IContractService contractService;
    private final IUserService userService;

    @Override
    public IPage<Copyright> page(Integer uid, Integer index, Integer limit, CopyrightPageQuery query) {
        IPage<Copyright> page = new Page<>(index, limit);
        if (query == null) {
            return page(page);
        }
        LambdaQueryWrapper<Copyright> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(uid != null, Copyright::getUserId, uid)
                .and(StringUtils.hasLength(query.getChainAddress()) || StringUtils.hasLength(query.getContent())
                        , w -> w.like(StringUtils.hasLength(query.getContent()),
                                        Copyright::getContent, query.getContent())
                                .or()
                                .eq(StringUtils.hasLength(query.getChainAddress())
                                        , Copyright::getChainAddress, query.getChainAddress()))
                .orderByDesc(Copyright::getCreateTime);
        return page(page, wrapper);
    }

    @Transactional
    @Override
    public Copyright create(Integer uid, CopyrightSaveDTO input) {
        Copyright copyright = Copyright
                .builder()
                .userId(uid)
                .content(input.getContent())
                .description(input.getDescription())
                .originFileUrl(input.getUrl())
                .watermarkFileUrl(input.getUrl())
                .md5Hash(HashUtil.string2HashHex("1", HashType.MD5))
                .sha1Hash(HashUtil.string2HashHex("2", HashType.SHA1))
                .sha256Hash(HashUtil.string2HashHex("3", HashType.SHA256))
                .build();
        if (!this.save(copyright)) {
            throw new ServiceException("保存异常");
        }
        User user = userService.getById(uid);
        String copyAddress = contractService.saveCopyright(
                user.getPrivateKey(),
                user.getContractChainAddress(),
                copyright);
        copyright.setChainAddress(copyAddress);
        updateById(copyright);
        return copyright;
    }

    @Override
    public Copyright getByChainAddress(Integer uid, String chainAddress) {
        User user = userService.getById(uid);
        String contractAddress = user.getContractChainAddress();
        try {
            return contractService.getCopyright(user.getPrivateKey(), contractAddress, chainAddress);
        } catch (ContractException e) {
            throw new ServiceException("智能合约执行异常", e);
        }
    }
}
