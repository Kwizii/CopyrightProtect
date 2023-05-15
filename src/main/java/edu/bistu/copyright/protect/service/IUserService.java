package edu.bistu.copyright.protect.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.bistu.copyright.protect.dto.UserCreateDTO;
import edu.bistu.copyright.protect.dto.UserLoginDTO;
import edu.bistu.copyright.protect.entity.User;
import edu.bistu.copyright.protect.vo.UserVO;
import org.fisco.bcos.sdk.v3.transaction.model.exception.ContractException;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Chanvo
 * @since 2023-05-08
 */
public interface IUserService extends IService<User> {

    Boolean createUser(UserCreateDTO input) throws ContractException, IOException;

    ResponseEntity<UserVO> login(UserLoginDTO input);

    User getByUsername(String username);
}
