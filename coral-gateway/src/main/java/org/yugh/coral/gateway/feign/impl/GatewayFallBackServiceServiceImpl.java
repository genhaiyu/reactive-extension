package org.yugh.coral.gateway.feign.impl;

import org.springframework.stereotype.Component;
import org.yugh.coral.core.config.hystrix.DefaultFallbackFactory;
import org.yugh.coral.gateway.feign.IGatewayFallBackService;

/**
 * @author yugenhai
 */
@Component
public class GatewayFallBackServiceServiceImpl extends DefaultFallbackFactory<IGatewayFallBackService> {

}
