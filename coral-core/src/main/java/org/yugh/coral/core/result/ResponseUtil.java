package org.yugh.coral.core.result;

import lombok.experimental.UtilityClass;
import org.yugh.coral.core.common.constant.StringPool;
import org.yugh.coral.core.common.exception.GlobalException;

import java.util.Objects;

/**
 * @author yugenhai
 */
@UtilityClass
public class ResponseUtil {


    /**
     * 检查调用接口响应
     *
     * @param ResponseData
     * @param <T>
     * @return
     */
    public static <T> T checkResult(ResponseData<T> ResponseData) {
        if (ResponseData == null) {
            throw new GlobalException(ResultCode.ERROR_MICROSERVICES, ResultCode.ERROR_MICROSERVICES.getMessage());
        }
        if (!Objects.equals(StringPool.SIX_ZEROS, ResponseData.getCode())) {
            if (ResponseData.getShowMessage()) {
                throw new GlobalException(ResultCode.ERROR, ResponseData.getMessage(), ResponseData.getShowMessage());
            } else {
                throw new GlobalException(ResultCode.ERROR, ResultCode.ERROR_MICROSERVICES.getMessage(), ResponseData.getShowMessage());
            }
        }
        return ResponseData.getData();
    }
}
