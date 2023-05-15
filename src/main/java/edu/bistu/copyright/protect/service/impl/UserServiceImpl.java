package edu.bistu.copyright.protect.service.impl;

import cn.dev33.satoken.secure.BCrypt;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.bistu.copyright.protect.dto.UserCreateDTO;
import edu.bistu.copyright.protect.dto.UserLoginDTO;
import edu.bistu.copyright.protect.entity.User;
import edu.bistu.copyright.protect.mapper.UserMapper;
import edu.bistu.copyright.protect.service.IContractService;
import edu.bistu.copyright.protect.service.IUserService;
import edu.bistu.copyright.protect.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.fisco.bcos.sdk.v3.crypto.CryptoSuite;
import org.fisco.bcos.sdk.v3.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.v3.model.CryptoType;
import org.fisco.bcos.sdk.v3.transaction.model.exception.ContractException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;

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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private final IContractService contractService;

    @Override
    public Boolean createUser(UserCreateDTO input) throws ContractException, IOException {
        CryptoSuite cryptoSuite = new CryptoSuite(CryptoType.ECDSA_TYPE);
        CryptoKeyPair keyPair = cryptoSuite.getCryptoKeyPair();
        String contractAddr = contractService.deployContract(keyPair.getHexPrivateKey());
        String hashpw = BCrypt.hashpw(input.getPassword(), BCrypt.gensalt());
        User user = User.builder()
                .username(input.getUsername())
                .password(hashpw)
                .privateKey(keyPair.getHexPrivateKey())
                .pubKey(keyPair.getHexPublicKey())
                .userChainAddress(keyPair.getAddress())
                .contractChainAddress(contractAddr)
                .build();
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
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @Override
    public User getByUsername(String username) {
        return getOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
    }
}
