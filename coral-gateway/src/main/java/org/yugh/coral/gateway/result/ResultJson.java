package org.yugh.coral.gateway.result;

import java.io.Serializable;

public final class ResultJson<T> implements Serializable {

    private static final long serialVersionUID = 2053742054576495115L;
    private Integer code;
    private String msg;
    private T result;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public static ResultJson ok() {
        return ok("");
    }

    public static ResultJson ok(Object o) {
        return new ResultJson(ResultEnum.SUCCESS, o);
    }

    public static ResultJson failure(ResultEnum code) {
        return failure(code, "");
    }

    public static ResultJson failure(ResultEnum code, Object o) {
        return new ResultJson(code, o);
    }

    public ResultJson(ResultEnum resultCode) {
        setResultCode(resultCode);
    }

    public ResultJson(ResultEnum resultCode, T result) {
        setResultCode(resultCode);
        this.result = result;
    }

    public void setResultCode(ResultEnum resultCode) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getValue();
    }

    @Override
    public String toString() {
        return "{" +
                "\"code\":" + code +
                ", \"msg\":\"" + msg + '\"' +
                ", \"data\":\"" + result + '\"' +
                '}';
    }
}
