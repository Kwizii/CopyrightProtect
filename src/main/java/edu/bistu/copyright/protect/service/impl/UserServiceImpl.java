package edu.bistu.copyright.protect.service.impl;

import cn.dev33.satoken.secure.BCrypt;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.bistu.copyright.protect.common.ServiceException;
import edu.bistu.copyright.protect.contract.CopyrightRepository;
import edu.bistu.copyright.protect.dto.UserCreateDTO;
import edu.bistu.copyright.protect.dto.UserLoginDTO;
import edu.bistu.copyright.protect.entity.User;
import edu.bistu.copyright.protect.mapper.UserMapper;
import edu.bistu.copyright.protect.service.IUserService;
import edu.bistu.copyright.protect.util.FiscoClient;
import edu.bistu.copyright.protect.vo.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fisco.bcos.sdk.v3.client.Client;
import org.fisco.bcos.sdk.v3.crypto.CryptoSuite;
import org.fisco.bcos.sdk.v3.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.v3.model.CryptoType;
import org.fisco.bcos.sdk.v3.transaction.model.exception.ContractException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {


    private final Client client = FiscoClient.instance();

    @Override
    public Boolean createUser(UserCreateDTO input) throws ContractException {
        if (getByUsername(input.getUsername()) != null) {
            throw new ServiceException("用户已存在");
        }
        CryptoSuite cryptoSuite = new CryptoSuite(CryptoType.ECDSA_TYPE);
        CryptoKeyPair keyPair = cryptoSuite.getCryptoKeyPair();
        CopyrightRepository repository = CopyrightRepository.deploy(client, keyPair);
        String contractAddr = repository.getContractAddress();
        String hashpw = BCrypt.hashpw(input.getPassword(), BCrypt.gensalt());
        User user = User.builder()
                .username(input.getUsername())
                .password(hashpw)
                .privateKey(keyPair.getHexPrivateKey())
                .pubKey(keyPair.getHexPublicKey())
                .userChainAddress(keyPair.getAddress())
                .contractChainAddress(contractAddr)
                .build();
        log.info("New user: {}", user);
        return this.save(user);
    }

    @Override
    public ResponseEntity<UserVO> login(UserLoginDTO input) {
        User user = getByUsername(input.getUsername());
        if (user != null && BCrypt.checkpw(input.getPassword(), user.getPassword())) {
            StpUtil.login(user.getId());
            return ResponseEntity.ok(new UserVO(user.getId(),
                    user.getUsername(),
                    user.getPubKey(),
                    user.getUserChainAddress(),
                    user.getContractChainAddress(),
                    StpUtil.getTokenValue()
            ));
        }
        throw new ServiceException("用户名或密码错误");
    }

    @Override
    public User getByUsername(String username) {
        return getOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
    }
}
