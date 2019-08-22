package org.yugh.gateway.feign.impl;

import org.springframework.stereotype.Component;
import org.yugh.auth.feign.DefaultFallbackFactory;
import org.yugh.gateway.feign.IGatewayTestService;

/**
 * @author yugenhai
 */
@Component
public class GatewayTestServiceImpl extends DefaultFallbackFactory<IGatewayTestService> {

}
