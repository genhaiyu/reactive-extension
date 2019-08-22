package org.yugh.gateway.reactor;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Token bucket
 * <p>
 * Is Traffic Shaping and Rate Limiting algorithm
 * <p>
 * Use Redis Reactor, is must
 * <p>
 * # X-RateLimit-Remaining -> Token bucket, Current Remaining = burstCapacity-replenishRate
 * # See {@link GatewayKeyResolver}
 * <p>
 * See application-dev.yml
 *
 * @author 余根海
 * @creation 2019-07-05 15:52
 * @Copyright © 2019 yugenhai. All rights reserved.
 */
@Component
public class GatewayKeyResolver implements KeyResolver {

    /**
     * since 5.0  HTTP request-response
     * <p>
     * When session is generated, happen request info
     * <p>
     * See application-dev.yml
     *
     * @param exchange
     * @return
     */
    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
        return Mono.just(exchange.getRequest().getRemoteAddress().getAddress().getHostAddress());
    }

}
