package reactive.extension.reactor.reactive;

import org.springframework.http.server.reactive.ServerHttpRequest;
import reactive.extension.basement.request.ReactiveWebContextHeader;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

/**
 * Publish and subscribe in reactive stream.
 * <p>
 * Origin see {@link ReactiveWebContextFilter}
 *
 * @author Genhai Yu
 */
public class ReactiveWebContextHolder {

    private static final Class<ServerHttpRequest> SERVER_HTTP_REQUEST_CLASS = ServerHttpRequest.class;
    private static final Class<ReactiveWebContextHeader> REACTIVE_CONTEXT_HEADER_CLASS = ReactiveWebContextHeader.class;

    /**
     * Gets the {@code Mono<ServerHttpRequest>} from Reactor {@link Context}
     *
     * @return the {@code Mono<ServerHttpRequest>}
     */
    public static Mono<ServerHttpRequest> getRequest() {
        return Mono.subscriberContext()
                .map(ctx -> ctx.get(SERVER_HTTP_REQUEST_CLASS));
    }

    /**
     * Gets the {@code Mono<ReactiveContextHeader>} from Reactor {@link Context}
     *
     * @return the Mono<ReactiveContextHeader>
     */
    public static Mono<ReactiveWebContextHeader> getReactiveContextHeader() {
        return Mono.subscriberContext()
                .map(ctx -> ctx.get(REACTIVE_CONTEXT_HEADER_CLASS));
    }

    /**
     * Put the {@code Mono<ReactiveContextHeader>} to Reactor {@link Context}
     *
     * @param context
     * @param reactiveWebContextHeader
     * @return the Reactor {@link Context}
     */
    public static Context setReactiveContextHeader(Context context, ReactiveWebContextHeader reactiveWebContextHeader) {
        return context.put(REACTIVE_CONTEXT_HEADER_CLASS, reactiveWebContextHeader);
    }


    /**
     * Put the {@code Mono<ServerHttpRequest>} to Reactor {@link Context}
     *
     * @param context
     * @param request
     * @return the Reactor {@link Context}
     */
    public static Context setServerHttpRequestReactor(Context context, ServerHttpRequest request) {
        return context.put(SERVER_HTTP_REQUEST_CLASS, request);
    }
}
