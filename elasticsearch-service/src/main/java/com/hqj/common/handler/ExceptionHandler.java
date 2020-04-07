package com.hqj.common.handler;

import com.hqj.common.dto.ResponseDTO;
import com.hqj.common.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理
 */
@ControllerAdvice
public class ExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(ExceptionHandler.class);

    /**
     * 处理异常返回
     *
     * @return
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(value = ServiceException.class)
    @ResponseBody
    public ResponseDTO serviceExceptionHandler(Exception e) {
        log.error(e.getMessage(), e);
        return ResponseDTO.fail(e.getMessage());
    }

    /**
     * 处理异常返回
     *
     * @return
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseDTO exceptionHandler(Exception e) {
        log.error(e.getMessage(), e);
        return ResponseDTO.fail(e.getMessage());
    }
}
