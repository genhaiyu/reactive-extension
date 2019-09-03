package org.yugh.gateway.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.yugh.auth.interceptor.PreFeignInterceptor;
import org.yugh.auth.util.ResultJson;
import org.yugh.gateway.feign.impl.GatewayFallBackServiceServiceImpl;

/**
 * Feign 测试
 *
 * @author yugenhai
 */
@Deprecated
@FeignClient(value = "BASE-SQL-SUBMISSION-SERVICE", fallbackFactory = GatewayFallBackServiceServiceImpl.class, configuration = PreFeignInterceptor.class)
public interface IGatewayFallBackService {

    /**
     * The method test case to HystrixCommand
     * And test case PreFeignAspect {@link org.yugh.auth.interceptor.PreFeignInterceptor}
     * See http://cwiki.xxxxx.com/pages/viewpage.action?pageId=113329378
     *
     * @return
     */
    @GetMapping(value = "/v1/metadata/connection")
    ResultJson loadConnectionSourceByUser();
}
