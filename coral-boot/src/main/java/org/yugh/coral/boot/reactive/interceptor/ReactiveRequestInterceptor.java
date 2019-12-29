package org.yugh.coral.boot.reactive.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.Assert;
import org.yugh.coral.boot.reactive.ReactiveRequestContextHolder;
import reactor.core.publisher.Mono;

/**
 * @author yugenhai
 */
@Deprecated
public class ReactiveRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        Mono<ServerHttpRequest> request = ReactiveRequestContextHolder.getRequest();
        Assert.notNull(request, () -> "ReactiveRequestInterceptor ReactiveRequestContextHolder.getRequest() '" + request + "' is null");
    }

}
