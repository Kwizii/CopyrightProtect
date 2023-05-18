package edu.bistu.copyright.protect.config;

import cn.dev33.satoken.exception.NotLoginException;
import edu.bistu.copyright.protect.common.ServiceException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Set;

/**
 * @author Chanvo
 * @date 2023/5/13 9:29
 * @description
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<String> handlerException(Exception e) {
        log.error("全局异常捕获：", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handlerException(MethodArgumentNotValidException e) {
        log.error("入参不满足条件：", e);
        StringBuilder message = new StringBuilder();
        List<FieldError> fieldErrors = e.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            message.append(fieldError.getDefaultMessage()).append(";");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message.toString());
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<String> handlerException(ServiceException e) {
        log.error("运行时错误：", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

    @ExceptionHandler(NotLoginException.class)
    public ResponseEntity<String> handlerException(NotLoginException e) {
        log.info("登录无效：", e);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("请登录账号");
    }
}
