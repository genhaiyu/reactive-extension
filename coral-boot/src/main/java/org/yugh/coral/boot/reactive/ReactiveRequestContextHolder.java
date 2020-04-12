/*
 *
 * Copyright (c) 2014-2020, yugenhai108@gmail.com.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 */


package org.yugh.coral.boot.reactive;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.yugh.coral.core.request.ReactiveContextHeader;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

/**
 * Add and Get Context info, in Reactor.
 *
 * Origin see {@link ReactiveRequestContextFilter}
 *
 * @author yugenhai
 */
public class ReactiveRequestContextHolder {

    private static final Class<ServerHttpRequest> SERVER_HTTP_REQUEST_CLASS = ServerHttpRequest.class;
    private static final Class<ReactiveContextHeader> REACTIVE_CONTEXT_HEADER_CLASS = ReactiveContextHeader.class;

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
    public static Mono<ReactiveContextHeader> getReactiveContextHeader() {
        return Mono.subscriberContext()
                .map(ctx -> ctx.get(REACTIVE_CONTEXT_HEADER_CLASS));
    }

    /**
     * Put the {@code Mono<ReactiveContextHeader>} to Reactor {@link Context}
     *
     * @param context
     * @param reactiveContextHeader
     * @return the Reactor {@link Context}
     */
    public static Context setReactiveContextHeader(Context context, ReactiveContextHeader reactiveContextHeader) {
        return context.put(REACTIVE_CONTEXT_HEADER_CLASS, reactiveContextHeader);
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
