package com.daniu.exception;

import com.daniu.common.ErrorCode;
import lombok.Getter;

/**
 * 自定义异常类
 *
 * @author FangDaniu
 * @since  2024/05/4
 */
@Getter
public class BusinessException extends RuntimeException {

    /**
     * 错误码
     */
    private final int code;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

    public BusinessException(ErrorCode errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
    }

}
