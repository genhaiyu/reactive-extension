package org.yugh.coral.boot.reactive;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.yugh.coral.core.pojo.bo.RequestHeaderBO;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

/**
 * @author yugenhai
 */
public class ReactiveRequestContextHolder {

    private static final Class<ServerHttpRequest> CONTEXT_KEY = ServerHttpRequest.class;

    private static final ThreadLocal<RequestHeaderBO> REQUEST_HEADER_KEY = new InheritableThreadLocal<>();

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
                .map(context -> context.get(REQUEST_HEADER_KEY_REACTOR));
    }

    /**
     * SpringMVC
     *
     * @param requestHeaderBO
     */
    public static void setRequestHeader(RequestHeaderBO requestHeaderBO) {
        REQUEST_HEADER_KEY.set(requestHeaderBO);
    }

    /**
     * SpringMVC
     *
     * @return
     */
    public static RequestHeaderBO getRequestHeader() {
        RequestHeaderBO requestHeaderBO = REQUEST_HEADER_KEY.get();
        if(requestHeaderBO == null){
            requestHeaderBO = new RequestHeaderBO();
        }
        return requestHeaderBO;
    }

    /**
     * SpringMVC
     */
    public static void resetRequestHeader() {
        REQUEST_HEADER_KEY.remove();
    }

    /**
     * Header By Mono
     *
     * @param context
     * @param requestHeaderBO
     * @return
     */
    public static Context setRequestHeaderReactor(Context context, RequestHeaderBO requestHeaderBO) {
        return context.put(REQUEST_HEADER_KEY_REACTOR, requestHeaderBO);
    }


    /**
     * Put the {@code Mono<ServerHttpRequest>} to Reactor {@link Context}
     *
     * @return the Reactor {@link Context}
     */
    public static Context put(Context context, ServerHttpRequest request) {
        return context.put(CONTEXT_KEY, request);
    }
}
