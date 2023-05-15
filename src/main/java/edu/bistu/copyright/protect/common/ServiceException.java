package edu.bistu.copyright.protect.common;

/**
 * @author Chanvo
 * @date 2023/5/15 15:44
 * @description
 */
public class ServiceException extends RuntimeException {

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
