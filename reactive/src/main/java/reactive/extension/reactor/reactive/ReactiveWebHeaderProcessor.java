package reactive.extension.reactor.reactive;

import org.slf4j.MDC;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactive.extension.basement.request.RequestHeaderProvider;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import java.util.Map;

import static java.util.stream.Collectors.toMap;

/**
 * Logging of the Reactive Context entry and exit
 *
 * @author Genhai Yu
 */
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
public class ReactiveWebHeaderProcessor implements WebFilter, Ordered {

    private int order = Ordered.HIGHEST_PRECEDENCE + 2;

    @Override
    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @NonNull
    @Override
    public Mono<Void> filter(ServerWebExchange ex, WebFilterChain chain) {
        assert ex != null;
        ex.getResponse().beforeCommit(
                () -> addContextToHttpResponseHeaders(ex.getResponse())
        );
        assert chain != null;
        return chain.filter(ex)
                .subscriberContext(
                        ctx -> addRequestHeadersToContext(ex.getRequest(), ctx)
                );
    }


    private Context addRequestHeadersToContext(final ServerHttpRequest request, final Context context) {
        MDC.put(RequestHeaderProvider.REQUEST_ID_KEY, request.getHeaders().toSingleValueMap().get(RequestHeaderProvider.REQUEST_ID_KEY));
        final Map<String, String> contextMap = request.getHeaders().toSingleValueMap().entrySet()
                .stream()
                .filter(req -> req.getKey().startsWith(RequestHeaderProvider.REQUEST_ID_KEY))
                .collect(toMap(
                        v -> v.getKey().substring(RequestHeaderProvider.REQUEST_ID_KEY.length()), Map.Entry::getValue));
        return context.put(RequestHeaderProvider.CONTEXT_MAP, contextMap);
    }

    private Mono<Void> addContextToHttpResponseHeaders(final ServerHttpResponse res) {
        return Mono.subscriberContext().doOnNext(ctx -> {
            if (!ctx.hasKey(RequestHeaderProvider.CONTEXT_MAP)) {
                return;
            }
            final HttpHeaders headers = res.getHeaders();
            ctx.<Map<String, String>>get(RequestHeaderProvider.CONTEXT_MAP).forEach(
                    (key, value) -> headers.add(RequestHeaderProvider.REQUEST_ID_KEY + key, value)
            );
            headers.add(RequestHeaderProvider.TIMESTAMP, String.valueOf(System.currentTimeMillis()));
            headers.keySet().forEach(MDC::remove);
        }).then();
    }
}