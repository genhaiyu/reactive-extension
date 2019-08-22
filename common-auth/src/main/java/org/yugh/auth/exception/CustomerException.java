package org.yugh.auth.exception;

import lombok.Getter;
import org.yugh.auth.util.JsonResult;

/**
 * // 自定义错误
 *
 * @author: 余根海
 * @creation: 2019-04-08 16:11
 * @Copyright © 2019 yugenhai. All rights reserved.
 */
@Getter
@Deprecated
public class CustomerException extends RuntimeException {

    private JsonResult jsonResult;

    public CustomerException(JsonResult jsonResult) {
        this.jsonResult = jsonResult;
    }
}
