package org.yugh.gatewaynew.feign.impl;

import org.springframework.stereotype.Component;
import org.yugh.auth.feign.DefaultFallbackFactory;
import org.yugh.gatewaynew.feign.IGatewayTestService;

/**
 * @author yugenhai
 */
@Component
public class GatewayTestServiceImpl extends DefaultFallbackFactory<IGatewayTestService> {

}
