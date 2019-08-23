/**
 * @author yugenhai
 */
package org.yugh.auth.exception;
/*
 *
 * @Getter
 * @Deprecated
 * public class CustomerException extends RuntimeException {
 * private JsonResult jsonResult;
 * public CustomerException(JsonResult jsonResult) {
 *    this.jsonResult = jsonResult;
 *  }
 *}
 *
 *
 * @RestControllerAdvice
 * @Deprecated
 * public class DefaultExceptionHandler {
 * 用户异常
 * @param e
 * @return
 * import org.springframework.web.bind.MethodArgumentNotValidException;
 * import org.springframework.web.bind.annotation.ExceptionHandler;
 * @ExceptionHandler(CustomerException.class)
 * public JsonResult handleCustomException(CustomerException e) {
 *    return e.getJsonResult();
 *   }
 * 处理所有自定义异常
 * @param e
 * @return
 * import org.springframework.web.bind.annotation.ExceptionHandler;
 * @ExceptionHandler(Exception.class)
 * public JsonResult exception(Exception e) {
 *    return JsonResult.buildErrorResult(HttpStatusEnum.INTERNAL_SERVER_ERROR.reasonPhraseCN(), e.getMessage());
 *  }
 * 处理参数校验异常
 * @param e
 * @return
 * import org.springframework.web.bind.annotation.ExceptionHandler;
 * @ExceptionHandler(MethodArgumentNotValidException.class)
 * public JsonResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
 *   return JsonResult.buildErrorResult(HttpStatusEnum.BAD_REQUEST.reasonPhraseCN(), e.getBindingResult().getFieldError().getDefaultMessage());
 * }
 *
 *
 */