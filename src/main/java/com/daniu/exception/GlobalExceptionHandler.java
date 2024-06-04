package com.daniu.exception;

import com.daniu.common.BaseResponse;
import com.daniu.common.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 * <p>
 * <p>
 * 用Sa-Token的通用返回类Sa-Token接管异常处理返回值
 *
 * @author FangDaniu
 * @since 2024/06/04
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    /**
     * 捕捉系统业务异常
     *
     * @param e e
     * @return {@link BaseResponse }<{@link String }>
     */
    @ExceptionHandler(BusinessException.class)
    public BaseResponse<String> businessExceptionHandler(BusinessException e) {
        log.error("BusinessException", e);
        return new BaseResponse<>(e.getCode(), e.getMessage());
    }

    /**
     * 捕捉其余所有异常
     *
     * @param e e
     * @return {@link BaseResponse }<{@link String }>
     */
    @ExceptionHandler
    public BaseResponse<String> handlerException(Exception e) {
        log.error("Exception", e);
        return new BaseResponse<>(ErrorCode.SYSTEM_ERROR.getCode(), e.getMessage());
    }

}
