package org.yugh.coral.auth.common.enums;

/**
 * @author: YuGenHai
 * @name: ResultEnum
 * @creation: 2018/9/21 16:57
 * @notes: 通用业务码
 */
public enum ResultEnum {

    SUCCESS(200, "成功"),
    BAD_REQUEST(400, "请求错误，请修正请求"),
    UNAUTHORIZED(401, "没有被授权或者授权已经失效"),
    LOGIN_ERROR(401, "登陆失败，用户名或密码无效"),
    FORBIDDEN(403, "请求被理解，但是拒绝执行"),
    NOT_FOUND(404, "请求的资源不存在"),
    OPERATE_ERROR(405, "请求方法不允许被执行"),
    TIME_OUT(408, "请求超时"),
    SERVER_ERROR(500, "服务器内部错误"),

    /**
     * 业务码
     */
    WHITE_NOT_FOUND(10001, "当前请求权限不足"),
    LOGIN_ERROR_GATEWAY(10002, "请通过SSO登录后访问"),
    GATEWAY_SERVER_ERROR(10003, "网关服务内部异常"),
    GATEWAY_DATA_CONVERT_ERROR(10004, "网关数据转换异常"),
    BLACK_SERVER_FOUND(10005, "禁止此方式访问服务网关"),
    HYSTRIX_ERROR(10006, "当前用户请求过于频繁，请歇会儿吧"),
    TOKEN_ILLEGAL(10007, "token非法"),
    TOKEN_EXPIRED(10008, "token已过期"),
    FEIGN_ERROR(10009, "Feign调用失败！");


    private Integer code;

    private String value;

    ResultEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public Integer getCode() {
        return code;
    }

    public String format(String v) {
        return String.format(this.value, v);
    }

    public String getValue() {
        return value;
    }

    public static ResultEnum codeOf(int code) {
        for (ResultEnum resultEnum : ResultEnum.values()) {
            if (resultEnum.code == code) {
                return resultEnum;
            }
        }
        return ResultEnum.SUCCESS;
    }
}
