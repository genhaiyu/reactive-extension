package org.yugh.gateway.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.yugh.auth.interceptor.PreFeignInterceptor;
import org.yugh.gateway.feign.impl.GatewayTestServiceImpl;

/**
 * @author yugenhai
 */
@FeignClient(value = "APP-LUBAN-DASHBOARD-SERVICE", fallbackFactory = GatewayTestServiceImpl.class, configuration = PreFeignInterceptor.class)
public interface IGatewayTestService {

    /**
     * The method test case to HystrixCommand
     * And test case PreFeignAspect {@link PreFeignInterceptor}
     * See http://xxxx.com/pages/viewpage.action?pageId=113329378
     *
     * @param id
     * @param name
     * @param createdAtStart
     * @param createdAtEnd
     * @param pageIndex
     * @param perPage
     * @return
     */
    @GetMapping(value = "/v1/chart/list")
    Object sendMsg1(@RequestParam(value = "id", defaultValue = "1") Integer id,
                   @RequestParam(value = "name", defaultValue = "2") String name,
                   @RequestParam(value = "createdAtStart", defaultValue = "1") String createdAtStart,
                   @RequestParam(value = "createdAtEnd", defaultValue = "2") String createdAtEnd,
                   @RequestParam(value = "pageIndex", defaultValue = "1") Integer pageIndex,
                   @RequestParam(value = "perPage", defaultValue = "10") Integer perPage);
}
