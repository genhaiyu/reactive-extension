package org.yugh.product.service.impl;

import org.springframework.stereotype.Component;
import org.yugh.auth.feign.DefaultFallbackFactory;
import org.yugh.product.service.IGatewayTestService;

/**
 * @author yugenhai
 */
@Component
public class GatewayTestServiceImpl extends DefaultFallbackFactory<IGatewayTestService> {

}
