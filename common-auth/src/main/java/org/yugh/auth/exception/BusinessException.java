package org.yugh.auth.exception;

import lombok.Getter;
import org.yugh.auth.common.enums.ResultEnum;

/**
 * 带业务枚举的异常
 *
 * @author: 余根海
 * @creation: 2019-04-08 16:11
 * @Copyright © 2019 yugenhai. All rights reserved.
 */
@Getter
public class BusinessException extends RuntimeException {

    private final ResultEnum resultEnum;
    private Object data;

    public BusinessException(ResultEnum resultEnum){
        this.resultEnum = resultEnum;
    }

    public BusinessException(ResultEnum resultEnum, Object data){
        this.resultEnum = resultEnum;
        this.data = data;
    }
}
