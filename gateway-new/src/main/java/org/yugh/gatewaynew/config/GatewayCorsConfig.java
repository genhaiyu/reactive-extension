package org.yugh.gatewaynew.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import org.yugh.globalauth.common.constants.Constant;
import reactor.core.publisher.Mono;

/**
 * Spring Cloud Gateway CORS setting
 * <p>
 * See {@link org.yugh.globalauth.filter.CorsFilter}
 *
 * @author yugenhai
 */
//@Component
//@Order(Ordered.HIGHEST_PRECEDENCE)
public class GatewayCorsConfig implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        if (CorsUtils.isCorsRequest(request)) {
            ServerHttpResponse response = exchange.getResponse();
            HttpHeaders headers = response.getHeaders();
            if (StringUtils.isEmpty(headers.get(HttpHeaders.ORIGIN))) {
                headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, Constant.XX_ALLOW_ALL);
            } else {
                headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, headers.get(HttpHeaders.ORIGIN).toString());
            }
            headers.add(HttpHeaders.ACCESS_CONTROL_MAX_AGE, String.valueOf(Constant.COOKIE_TIME_OUT));
            headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
            headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, Constant.XX_ALLOW_ALL);
            headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, Constant.XX_ALLOW_HEADERS);
            if (request.getMethod() == HttpMethod.OPTIONS) {
                response.setStatusCode(HttpStatus.OK);
                return Mono.empty();
            }
        }
        return chain.filter(exchange);
    }

}
