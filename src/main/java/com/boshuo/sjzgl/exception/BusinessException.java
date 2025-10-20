package com.boshuo.sjzgl.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class BusinessException extends RuntimeException {
    private Integer code;
    private String message;

    public BusinessException(String message) {
        super(message);
        this.message = message;
        this.code = 500;
    }
    public BusinessException(Integer code, String message) {
        super(message);
        this.message = message;
        this.code = code;
    }
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
        this.code = 500;
    }
}
