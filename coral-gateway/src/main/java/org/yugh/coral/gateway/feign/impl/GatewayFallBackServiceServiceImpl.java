package org.yugh.coral.gateway.feign.impl;

import org.springframework.stereotype.Component;
import org.yugh.coral.boot.config.feign.DefaultFallbackFactory;
import org.yugh.coral.gateway.feign.IGatewayFallBackService;

/**
 * @author yugenhai
 */
@Component
public class GatewayFallBackServiceServiceImpl extends DefaultFallbackFactory<IGatewayFallBackService> {

}
