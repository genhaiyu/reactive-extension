package org.yugh.gatewaynew.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.yugh.gatewaynew.feign.impl.GatewayTestServiceImpl;
import org.yugh.globalauth.aspect.PreFeignAspect;

/**
 * @author yugenhai
 */
@FeignClient(value = "service1", fallback = GatewayTestServiceImpl.class, configuration = PreFeignAspect.class)
public interface IGatewayTestService {

    @RequestMapping(value = "/index/list")
    Object sendMsg();
}
