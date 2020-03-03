package org.yugh.coral.gateway.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.yugh.coral.boot.servlet.interceptor.FeignRequestInterceptor;
import org.yugh.coral.gateway.feign.impl.GatewayFallBackServiceServiceImpl;
import org.yugh.coral.gateway.result.ResultJson;

/**
 * Feign 测试
 *
 * @author yugenhai
 */
@Deprecated
@FeignClient(value = "BASE-SQL-SUBMISSION-SERVICE", fallbackFactory = GatewayFallBackServiceServiceImpl.class, configuration = FeignRequestInterceptor.class)
public interface IGatewayFallBackService {

    @GetMapping(value = "/v1/metadata/connection")
    ResultJson loadConnectionSourceByUser();
}
