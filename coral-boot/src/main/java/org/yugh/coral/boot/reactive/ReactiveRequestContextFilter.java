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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import org.yugh.coral.core.config.DispatcherRequestCustomizer;
import org.yugh.coral.core.config.RequestAdapterProvider;
import org.yugh.coral.core.request.ReactiveContextHeader;
import org.yugh.coral.core.request.RequestHeaderProvider;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Reactive stream request filter.
 *
 * @author yugenhai
 */
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
public class ReactiveRequestContextFilter implements WebFilter, Ordered {

    private static final Logger LOG = LoggerFactory.getLogger(ReactiveRequestContextFilter.class);
    private final Map<String, Object> contextMap = new ConcurrentHashMap<>();
    private final RequestAdapterProvider.ProduceValues produceValues = new RequestAdapterProvider.ProduceValues();
    private final ReactiveContextHeader<Map<String, Object>, Set<String>> reactiveContextHeader = new ReactiveContextHeader<>();
    // filter should be set
    private int order = Ordered.HIGHEST_PRECEDENCE + 1;
    private final DispatcherRequestCustomizer<RequestAdapterProvider.ProduceValues> dispatcherRequestCustomizer;


    public ReactiveRequestContextFilter(DispatcherRequestCustomizer<RequestAdapterProvider.ProduceValues> dispatcherRequestCustomizer) {
        this.dispatcherRequestCustomizer = dispatcherRequestCustomizer;
    }

    @Override
    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }


    @Override
    @Nullable
    public Mono<Void> filter(@Nullable ServerWebExchange exchange, @Nullable WebFilterChain chain) {
        Assert.notNull(exchange, () -> "ServerWebExchange '" + exchange + "' must not be null");
        Assert.notNull(chain, () -> "WebFilterChain '" + chain + "' must not be null");
        ServerHttpRequest request = exchange.getRequest();
        dispatcherRequestCustomizer.customize(produceValues);

        if (produceValues.getMessageId() == null) {
            throw new IllegalArgumentException("idProvider initialization failed");
        }
        String idProvider = produceValues.getMessageId();
        exchange.getRequest().mutate().headers(httpHeaders -> httpHeaders.add(RequestHeaderProvider.REQUEST_ID_KEY, idProvider));
        contextMap.put(RequestHeaderProvider.CONTEXT_MAP, idProvider);
        reactiveContextHeader.setIds(Stream.of(idProvider, request.getId()).collect(Collectors.toSet()));
        reactiveContextHeader.setContextMap(contextMap);
        LOG.info("Reactive Request Started : {}", idProvider);
        return chain.filter(exchange).
                subscriberContext(ctx ->
                        ReactiveRequestContextHolder.setServerHttpRequestReactor(ctx, request))
                .subscriberContext(ctx ->
                        ReactiveRequestContextHolder.setReactiveContextHeader(ctx, reactiveContextHeader));
    }
}
