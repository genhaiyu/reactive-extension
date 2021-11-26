package reactive.extension.reactor.reactive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactive.extension.basement.config.DispatcherRequestCustomizer;
import reactive.extension.basement.request.ReactiveWebContextHeader;
import reactive.extension.basement.request.RequestHeaderProvider;
import reactive.extension.basement.request.RequestMessageDefinition;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Logging of the Reactive Context entry and exit
 *
 * @author Genhai Yu
 */
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
public class ReactiveWebContextFilter implements WebFilter, Ordered {

    private static final Logger LOG = LoggerFactory.getLogger(ReactiveWebContextFilter.class);
    private final Map<String, Object> contextMap = new ConcurrentHashMap<>();
    private final RequestMessageDefinition.ProduceValues produceValues = new RequestMessageDefinition.ProduceValues();
    private final ReactiveWebContextHeader<Map<String, Object>, Set<String>> reactiveWebContextHeader = new ReactiveWebContextHeader<>();
    private int order = Ordered.HIGHEST_PRECEDENCE + 1;
    private final DispatcherRequestCustomizer<RequestMessageDefinition.ProduceValues> dispatcherRequestCustomizer;


    public ReactiveWebContextFilter(DispatcherRequestCustomizer<RequestMessageDefinition.ProduceValues> dispatcherRequestCustomizer) {
        this.dispatcherRequestCustomizer = dispatcherRequestCustomizer;
    }

    @Override
    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @NonNull
    @Override
    public Mono<Void> filter(@NonNull ServerWebExchange exchange, @NonNull WebFilterChain chain) {
        Assert.notNull(exchange, () -> "ServerWebExchange '" + exchange + "' should be not be null");
        Assert.notNull(chain, () -> "WebFilterChain '" + chain + "' should be not be null");
        ServerHttpRequest request = exchange.getRequest();
        dispatcherRequestCustomizer.customize(produceValues);

        if (produceValues.getMessageId() == null) {
            throw new IllegalArgumentException("The ID generation strategy has failed");
        }
        String idProvider = produceValues.getMessageId();
        exchange.getRequest().mutate().headers(httpHeaders -> httpHeaders.add(RequestHeaderProvider.REQUEST_ID_KEY, idProvider));
        contextMap.put(RequestHeaderProvider.CONTEXT_MAP, idProvider);
        reactiveWebContextHeader.initialReactWebContextHeader(Stream.of(idProvider, request.getId()).collect(Collectors.toSet()), contextMap);
        LOG.info("Reactive Request Started : {}", idProvider);
        return chain.filter(exchange).
                subscriberContext(ctx ->
                        ReactiveWebContextHolder.setServerHttpRequestReactor(ctx, request))
                .subscriberContext(ctx ->
                        ReactiveWebContextHolder.setReactiveContextHeader(ctx, reactiveWebContextHeader));
    }
}
