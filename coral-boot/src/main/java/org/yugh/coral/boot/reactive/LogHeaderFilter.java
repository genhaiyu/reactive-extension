package org.yugh.coral.boot.reactive;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import org.yugh.coral.core.common.constant.StringPool;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import java.util.Map;

import static java.util.stream.Collectors.toMap;

/**
 * @author yugenhai
 */
@Slf4j
@Order(Integer.MIN_VALUE)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
public class LogHeaderFilter implements WebFilter {


    @Nullable
    @Override
    public Mono<Void> filter(@Nullable ServerWebExchange ex, @Nullable WebFilterChain chain) {
        ex.getResponse().beforeCommit(
                () -> addContextToHttpResponseHeaders(ex.getResponse())
        );
        return chain.filter(ex)
                .subscriberContext(
                        ctx -> addRequestHeadersToContext(ex.getRequest(), ctx)
                );
    }

    private Context addRequestHeadersToContext(final ServerHttpRequest request, final Context context) {
        final Map<String, String> contextMap = request.getHeaders().toSingleValueMap().entrySet()
                .stream()
                .filter(x -> x.getKey().startsWith(StringPool.MDC_HEADER))
                .collect(toMap(
                        v -> v.getKey().substring(StringPool.MDC_HEADER.length()),
                        Map.Entry::getValue
                        )
                );
        return context.put(StringPool.CONTEXT_MAP, contextMap);
    }

    private Mono<Void> addContextToHttpResponseHeaders(final ServerHttpResponse res) {
        return Mono.subscriberContext().doOnNext(ctx -> {
            if (!ctx.hasKey(StringPool.CONTEXT_MAP)) {
                return;
            }
            final HttpHeaders headers = res.getHeaders();
            ctx.<Map<String, String>>get(StringPool.CONTEXT_MAP).forEach(
                    (key, value) -> headers.add(StringPool.MDC_HEADER + key, value)
            );
        }).then();
    }
}
