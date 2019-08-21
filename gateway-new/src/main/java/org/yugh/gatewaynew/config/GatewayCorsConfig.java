package corg.yugh.gatewaynew.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import org.yugh.auth.common.constants.Constant;
import reactor.core.publisher.Mono;

/**
 * Spring Cloud Gateway CORS setting
 *
 * @author yugenhai
 */
@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GatewayCorsConfig implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        if (CorsUtils.isCorsRequest(request)) {
            ServerHttpResponse response = exchange.getResponse();
            if (StringUtils.isEmpty(request.getHeaders().getFirst(HttpHeaders.ORIGIN))) {
                response.getHeaders().add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, Constant.xx_ALLOW_ALL);
            } else {
                response.getHeaders().add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, request.getHeaders().getFirst(HttpHeaders.ORIGIN));
            }
            response.getHeaders().add(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
            response.getHeaders().add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, Constant.xx_ALLOW_ALL);
            response.getHeaders().add(HttpHeaders.ACCESS_CONTROL_MAX_AGE, String.valueOf(Constant.COOKIE_TIME_OUT));
            response.getHeaders().add(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, Constant.xx_ALLOW_HEADERS);
        }
        return chain.filter(exchange);
    }
}
