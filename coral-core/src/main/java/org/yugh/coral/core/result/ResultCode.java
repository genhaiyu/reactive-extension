package org.yugh.coral.core.result;

import java.util.HashMap;
import java.util.Map;

public enum ResultCode {

    /**
     * 成功
     */
    SUCCESS("000000", "成功"),

    /**
     * app success code
     */
    APP_SUCCESS("0", "成功"),


    /**
     * 系统异常
     * AA = 99
     */
    ERROR("999999", "系统异常"),
    ERROR_PARAMETER("999998", "参数错误"),
    ERROR_DATA_DUPLICATE("999997", "数据重复"),
    ERROR_DATABASE("999996", "数据库异常"),
    ERROR_RESOURCE_NOT_FOUND("999995", "资源未找到"),
    /**
     * 微服务调用异常,包括404、timeout
     */
    ERROR_MICROSERVICES("999994", "微服务调用异常"),
    ERROR_APP_COMMON("999993", "系统开小差了，请稍后再试~"),
    ERROR_SGIN("999992", "签名验证失败"),
    ERROR_ENCRYPTION("999991", "加密失败");


    private static Map<String, ResultCode> resultCodeMap2Code = new HashMap<>();

    static {
        for (ResultCode resultCode : ResultCode.values()) {
            resultCodeMap2Code.put(resultCode.code, resultCode);
        }
    }

    /**
     * code
     */
    public String code;
    /**
     * 信息
     */
    public String message;

    ResultCode(String code, String message) {
        this.setCode(code);
        this.setMessage(message);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static ResultCode getResultCodeByCode(String code) {
        return resultCodeMap2Code.getOrDefault(code, null);
    }
}
