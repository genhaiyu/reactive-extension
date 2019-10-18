package org.yugh.zipkin.sentinel.feign;

import org.yugh.zipkin.sentinel.feign.impl.CallResponseServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author genhai yu
 */
@FeignClient(value = "zipkin-response", fallback = CallResponseServiceImpl.class)
public interface ICallResponseService {

    /**
     * 呼叫response
     *
     * @param name
     * @return
     */
    @RequestMapping(value = {"/resp/callResponse"}, method = {RequestMethod.GET})
    String callResponse(@RequestParam(value = "name") String name);
}
