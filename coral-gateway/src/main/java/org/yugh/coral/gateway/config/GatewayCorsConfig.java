package org.yugh.coral.gateway.config;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * Spring Cloud Gateway CORS setting
 *
 * @author yugenhai
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GatewayCorsConfig implements WebFilter {

    private static final String ALLOW_HEADERS = "X-Requested-with,REQUEST_ID,Cache-Control,Expires,Content-Type,Authorization,X-Requested-ID";

    @Override
    @Nullable
    public Mono<Void> filter(@NonNull ServerWebExchange exchange,@NonNull WebFilterChain chain) {
        Assert.notNull(exchange, () -> "ServerWebExchange '" + exchange + "' is null");
        Assert.notNull(chain, () -> "WebFilterChain '" + chain + "' is null");
        ServerHttpRequest request = exchange.getRequest();
        if (CorsUtils.isCorsRequest(request)) {
            ServerHttpResponse response = exchange.getResponse();
            if (StringUtils.isEmpty(request.getHeaders().getFirst(HttpHeaders.ORIGIN))) {
                response.getHeaders().add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
            } else {
                response.getHeaders().add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, request.getHeaders().getFirst(HttpHeaders.ORIGIN));
            }
            response.getHeaders().add(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
            response.getHeaders().add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "*");
            response.getHeaders().add(HttpHeaders.ACCESS_CONTROL_MAX_AGE, String.valueOf(30));
            response.getHeaders().add(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, ALLOW_HEADERS);
        }
        return chain.filter(exchange);
    }
}
