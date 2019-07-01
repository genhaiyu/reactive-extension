package org.yugh.gateway.common.enums;
/**
 * @author yugenhai
 * 返回枚举
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
    SERVER_ERROR(500, "服务器内部错误");

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
