package edu.bistu.copyright.protect.controller;

import cn.dev33.satoken.stp.StpUtil;
import edu.bistu.copyright.protect.dto.UserCreateDTO;
import edu.bistu.copyright.protect.dto.UserLoginDTO;
import edu.bistu.copyright.protect.entity.User;
import edu.bistu.copyright.protect.service.IUserService;
import edu.bistu.copyright.protect.vo.UserVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fisco.bcos.sdk.v3.transaction.model.exception.ContractException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Chanvo
 * @since 2023-05-08
 */
@Tag(name = "用户控制器")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final IUserService userService;

    @Operation(summary = "根据ID获得User对象")
    @Parameter(name = "id", description = "用户ID", required = true)
    @GetMapping("/get/{id}")
    public ResponseEntity<User> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @Operation(summary = "用户注册")
    @PostMapping("/register")
    public ResponseEntity<Boolean> createUser(@RequestBody UserCreateDTO input) {
        try {
            return ResponseEntity.ok(userService.createUser(input));
        } catch (ContractException | IOException e) {
            log.error("Create user error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "用户登录")
    @GetMapping("/login")
    public ResponseEntity<UserVO> login(@Parameter UserLoginDTO user) {
        return userService.login(user);
    }

    @Operation(summary = "用户注销")
    @GetMapping("/logout")
    public ResponseEntity<Boolean> logout() {
        StpUtil.logout();
        return ResponseEntity.ok(Boolean.TRUE);
    }
}
