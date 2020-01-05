package org.yugh.coral.boot.reactive;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.yugh.coral.core.pojo.bo.RequestHeaderBO;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

/**
 * @author yugenhai
 */
public class CustomRequestContextHolder {

    private static final Class<ServerHttpRequest> CONTEXT_KEY = ServerHttpRequest.class;
    private static final Class<RequestHeaderBO> REQUEST_HEADER_KEY_REACTOR = RequestHeaderBO.class;

    /**
     * Gets the {@code Mono<ServerHttpRequest>} from Reactor {@link Context}
     *
     * @return the {@code Mono<ServerHttpRequest>}
     */
    public static Mono<ServerHttpRequest> getRequest() {
        return Mono.subscriberContext()
                .map(ctx -> ctx.get(CONTEXT_KEY));
    }

    /**
     * Gets the {@code Mono<RequestHeaderBO>} from Reactor {@link Context}
     *
     * @return the Mono<RequestHeaderBO>
     */
    public static Mono<RequestHeaderBO> getRequestHeaderReactor() {
        return Mono.subscriberContext()
                .map(ctx -> ctx.get(REQUEST_HEADER_KEY_REACTOR));
    }

    /**
     * Put the {@code Mono<RequestHeaderBO>} to Reactor {@link Context}
     *
     * @param context
     * @param requestHeaderBO
     * @return the Reactor {@link Context}
     */
    public static Context setRequestHeaderReactor(Context context, RequestHeaderBO requestHeaderBO) {
        return context.put(REQUEST_HEADER_KEY_REACTOR, requestHeaderBO);
    }


    /**
     * Put the {@code Mono<ServerHttpRequest>} to Reactor {@link Context}
     *
     * @param context
     * @param request
     * @return the Reactor {@link Context}
     */
    public static Context setServerHttpRequestReactor(Context context, ServerHttpRequest request) {
        return context.put(CONTEXT_KEY, request);
    }
}
