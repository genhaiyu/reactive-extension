package org.yugh.coral.core.common.exception;

import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.yugh.coral.core.result.ResultCode;

/**
 * @author yugenhai
 */
@NoArgsConstructor
public class GlobalException extends RuntimeException {

    /**
     * 错误码
     */
    private ResultCode code;
    /**
     * 传递给 ResponseData 的指令，异常信息是否需要显示
     */
    private Boolean showMessage;

    /**
     * 抛出异常打印message信息
     *
     * @param code
     */
    public GlobalException(ResultCode code) {
        super(code.message);
        this.code = code;
    }

    /**
     * 没有错误码，默认错误
     *
     * @param message
     */
    public GlobalException(String message) {
        super(message);
        this.code = ResultCode.ERROR;
    }

    /**
     * 抛出异常打印message信息
     *
     * @param code
     * @param message
     */
    public GlobalException(ResultCode code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * 是否显示错误信息
     *
     * @param code
     * @param message
     * @param showMessage
     */
    public GlobalException(ResultCode code, String message, Boolean showMessage) {
        super(message);
        this.code = code;
        this.showMessage = showMessage;
    }

    /**
     * 是否显示错误信息, 不带 message
     *
     * @param code
     * @param showMessage
     */
    public GlobalException(ResultCode code, Boolean showMessage) {
        super(code.getMessage());
        this.code = code;
        this.showMessage = showMessage;
    }

    /**
     * 调用父类抛出
     *
     * @param message
     * @param cause
     */
    public GlobalException(String message, Throwable cause) {
        super(message, cause);
    }


    public ResultCode getCode() {
        return code;
    }

    public void setCode(ResultCode code) {
        this.code = code;
    }

    public Boolean getShowMessage() {
        return showMessage;
    }

    public void setShowMessage(Boolean showMessage) {
        this.showMessage = showMessage;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("code", code)
                .append("showMessage", showMessage)
                .toString();
    }
}
