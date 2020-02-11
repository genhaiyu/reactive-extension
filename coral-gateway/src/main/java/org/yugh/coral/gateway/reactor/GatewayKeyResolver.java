package org.yugh.coral.gateway.reactor;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * # 令牌桶示例可见
 * # https://api.github.com/
 *
 * @author 余根海
 * @creation 2019-07-05 15:52
 * @Copyright © 2019 yugenhai. All rights reserved.
 */
@Component
public class GatewayKeyResolver implements KeyResolver {

    /**
     * 当前是 Ip 限流
     *
     * @param exchange
     * @return
     */
    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
        return Mono.just(exchange.getRequest().getRemoteAddress().getAddress().getHostAddress());
    }

    /**
     * Modify implements Redis
     *
     * FIXME
     */


    //令牌桶示例可见
    //https://api.github.com/


}
