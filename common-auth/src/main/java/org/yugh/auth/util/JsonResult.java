package org.yugh.auth.util;

import lombok.ToString;

@ToString
public class JsonResult<T> {

    private boolean success;
    private String message = "success";
    private T data;

    private JsonResult(){}


    public static JsonResult buildSuccessResult(){
        JsonResult jsonResult = new JsonResult();
        jsonResult.setSuccess(true);
        return jsonResult;
    }

    public static JsonResult buildSuccessResult(String message){
        JsonResult jsonResult = new JsonResult();
        jsonResult.setSuccess(true);
        jsonResult.setMessage(message);
        return jsonResult;
    }

    public static JsonResult buildSuccessResult(Object data){
        JsonResult jsonResult = new JsonResult();
        jsonResult.setSuccess(true);
        jsonResult.setData(data);
        return jsonResult;
    }

    public static JsonResult buildSuccessResult(Object data,String message){
        JsonResult jsonResult = new JsonResult();
        jsonResult.setSuccess(true);
        jsonResult.setData(data);
        jsonResult.setMessage(message);
        return jsonResult;
    }

    public static JsonResult buildErrorResult(){
        JsonResult jsonResult = new JsonResult();
        jsonResult.setSuccess(false);
        jsonResult.setMessage("fail");
        return jsonResult;
    }

    public static JsonResult buildErrorResult(String message){
        JsonResult jsonResult = new JsonResult();
        jsonResult.setSuccess(false);
        jsonResult.setMessage(message);
        return jsonResult;
    }

    public static JsonResult buildErrorResult(Exception e){
        JsonResult jsonResult = new JsonResult();
        jsonResult.setSuccess(false);
        jsonResult.setMessage(e.getMessage());
        return jsonResult;
    }

    public static JsonResult buildErrorResult(Object data,String message){
        JsonResult jsonResult = new JsonResult();
        jsonResult.setSuccess(false);
        jsonResult.setData(data);
        jsonResult.setMessage(message);
        return jsonResult;
    }


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T obj) {
        data = obj;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
