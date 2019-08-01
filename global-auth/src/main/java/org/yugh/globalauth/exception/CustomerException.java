package org.yugh.globalauth.exception;

import lombok.Getter;
import org.yugh.globalauth.util.ResultJson;

/**
 * // 自定义错误
 *
 * @author: 余根海
 * @creation: 2019-04-08 16:11
 * @Copyright © 2019 yugenhai. All rights reserved.
 */
@Getter
public class CustomerException extends RuntimeException {

    private ResultJson resultJson;

    public CustomerException(ResultJson resultJson) {
        this.resultJson = resultJson;
    }
}
