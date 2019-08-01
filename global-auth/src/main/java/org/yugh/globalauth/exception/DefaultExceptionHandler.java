package org.yugh.globalauth.exception;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.yugh.globalauth.common.enums.ResultEnum;
import org.yugh.globalauth.util.ResultJson;

/**
 * // 错误拦截
 *
 * @author: 余根海
 * @creation: 2019-04-08 16:15
 * @Copyright © 2019 yugenhai. All rights reserved.
 */
//@RestControllerAdvice
public class DefaultExceptionHandler {

    /**
     * 用户异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(CustomerException.class)
    public ResultJson handleCustomException(CustomerException e) {
        return e.getResultJson();
    }

    /**
     * 处理所有自定义异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResultJson exception(Exception e) {
        return ResultJson.failure(ResultEnum.SERVER_ERROR, e.getMessage());
    }

    /**
     * 处理参数校验异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultJson handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return ResultJson.failure(ResultEnum.PARAM_ERROR, e.getBindingResult().getFieldError().getDefaultMessage());
    }
}
