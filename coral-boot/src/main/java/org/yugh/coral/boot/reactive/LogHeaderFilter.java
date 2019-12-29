package org.yugh.coral.boot.reactive;

import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import java.util.Map;

import static java.util.stream.Collectors.toMap;

/**
 * @author yugenhai
 */
public class LogHeaderFilter implements WebFilter {

    private static final String MDC_HEADER_PREFIX = "X-MDC-";
    public static final String CONTEXT_MAP = "context-map";

    @Nullable
    @Override
    public Mono<Void> filter(ServerWebExchange ex, WebFilterChain chain) {
        ex.getResponse().beforeCommit(
                () -> addContextToHttpResponseHeaders(ex.getResponse())
        );
        return chain.filter(ex)
                .subscriberContext(
                        ctx -> addRequestHeadersToContext(ex.getRequest(), ctx)
                );
    }

    private Context addRequestHeadersToContext(final ServerHttpRequest request, final Context context) {
        final Map<String, String> contextMap = request
                .getHeaders().toSingleValueMap().entrySet()
                .stream()
                .filter(x -> x.getKey().startsWith(MDC_HEADER_PREFIX))
                .collect(
                        toMap(v -> v.getKey().substring(MDC_HEADER_PREFIX.length()),
                                Map.Entry::getValue
                        )
                );
        return context.put(CONTEXT_MAP, contextMap);
    }

    private Mono<Void> addContextToHttpResponseHeaders(final ServerHttpResponse res) {
        return Mono.subscriberContext().doOnNext(ctx -> {
            if (!ctx.hasKey(CONTEXT_MAP)) {
                return;
            }
            final HttpHeaders headers = res.getHeaders();
            ctx.<Map<String, String>>get(CONTEXT_MAP).forEach(
                    (key, value) -> headers.add(MDC_HEADER_PREFIX + key, value)
            );
        }).then();
    }
}
