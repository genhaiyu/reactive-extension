package org.yugh.gatewaynew.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * //令牌桶IP限流
 *
 * @author 余根海
 * @creation 2019-07-05 15:52
 * @Copyright © 2019 yugenhai. All rights reserved.
 */
@Component
public class GatewayKeyResolver implements KeyResolver {

    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
        return Mono.just(exchange.getRequest().getRemoteAddress().getAddress().getHostAddress());
    }

}
