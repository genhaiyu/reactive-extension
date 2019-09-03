package org.yugh.gateway.feign.impl;

import org.springframework.stereotype.Component;
import org.yugh.auth.factory.DefaultFallbackFactory;
import org.yugh.gateway.feign.IGatewayFallBackService;

/**
 * @author yugenhai
 */
@Component
public class GatewayFallBackServiceServiceImpl extends DefaultFallbackFactory<IGatewayFallBackService> {

}
