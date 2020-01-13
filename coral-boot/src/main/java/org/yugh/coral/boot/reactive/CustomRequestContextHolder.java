package org.yugh.coral.boot.reactive;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.yugh.coral.core.pojo.bo.RequestContextBO;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

/**
 * ${@link ServerHttpRequest} ${@link RequestContextBO} 透传并发布到 Context 中 {@link Context}
 * <p>
 * 可以在微服务线程隔离下透传当前会话
 *
 * @author yugenhai
 */
public class CustomRequestContextHolder {

    private static final Class<ServerHttpRequest> CONTEXT_KEY = ServerHttpRequest.class;
    private static final Class<RequestContextBO> REQUEST_CONTEXT_REACTOR = RequestContextBO.class;

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
    public static Mono<RequestContextBO> getRequestHeaderReactor() {
        return Mono.subscriberContext()
                .map(ctx -> ctx.get(REQUEST_CONTEXT_REACTOR));
    }

    /**
     * Put the {@code Mono<RequestHeaderBO>} to Reactor {@link Context}
     *
     * @param context
     * @param requestContextBO
     * @return the Reactor {@link Context}
     */
    public static Context setRequestHeaderReactor(Context context, RequestContextBO requestContextBO) {
        return context.put(REQUEST_CONTEXT_REACTOR, requestContextBO);
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
