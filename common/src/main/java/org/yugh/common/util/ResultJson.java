package org.yugh.common.util;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.yugh.common.common.enums.ResultEnum;

import java.io.Serializable;

/**
 * //返回数据
 *
 * @author: 余根海
 * @creation: 2019-04-08 15:55
 * @Copyright © 2019 yugenhai. All rights reserved.
 */
@Data
@NoArgsConstructor
public final class ResultJson<T> implements Serializable {

    @Setter(AccessLevel.PRIVATE)
    private Integer code;
    @Setter(AccessLevel.PRIVATE)
    private String msg;
    @Setter(AccessLevel.PRIVATE)
    private T result;


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
