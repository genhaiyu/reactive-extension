package org.yugh.auth.hystrix;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.yugh.auth.common.constants.Constant;
import org.yugh.auth.common.enums.ResultEnum;
import org.yugh.auth.util.ResultJson;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Hystrix default controller
 *
 * @author yugenhai
 */
@Slf4j
@DefaultProperties(defaultFallback = "hystrixDefaultFallback")
public abstract class AbstractHystrixController {


    /**
     * hystrix default
     *
     * @return
     */
    private ResultJson hystrixDefaultFallback() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        Map<String, Object> resultMap = new HashMap(16);
        resultMap.put("rpid", request.getAttribute(Constant.GLOBAL_RPID));
        resultMap.put("user", request.getAttribute(Constant.USER_INFO));
        log.info("\n ******* Current Hystrix Rpid : {}, Current Hystrix Info : {}", request.getAttribute(Constant.GLOBAL_RPID), resultMap);
        return ResultJson.failure(ResultEnum.HYSTRIX_ERROR, resultMap);
    }

}
