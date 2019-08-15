package org.yugh.globalauth.exception;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.yugh.globalauth.common.enums.HttpStatusEnum;
import org.yugh.globalauth.util.JsonResult;

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
    public JsonResult handleCustomException(CustomerException e) {
        return e.getJsonResult();
    }

    /**
     * 处理所有自定义异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public JsonResult exception(Exception e) {
        return JsonResult.buildErrorResult(HttpStatusEnum.INTERNAL_SERVER_ERROR.reasonPhraseCN(), e.getMessage());
    }

    /**
     * 处理参数校验异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public JsonResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return JsonResult.buildErrorResult(HttpStatusEnum.BAD_REQUEST.reasonPhraseCN(), e.getBindingResult().getFieldError().getDefaultMessage());
    }
}
