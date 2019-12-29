package org.yugh.coral.boot.reactive;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import org.yugh.coral.core.common.constant.StringPool;
import org.yugh.coral.core.utils.SnowFlakeGenerateUtils;
import reactor.core.publisher.Mono;
import reactor.util.annotation.Nullable;

/**
 * @author yugenhai
 */
@Slf4j
@Order(Integer.MIN_VALUE)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
public class ReactiveRequestContextFilter implements WebFilter {

    @Override
    @Nullable
    public Mono<Void> filter(@Nullable ServerWebExchange exchange, @Nullable WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        Thread thread = Thread.currentThread();
        String msgId = String.valueOf(SnowFlakeGenerateUtils.snowFlakeGenerate());
        thread.setName(msgId);
        exchange.getRequest().mutate().header(StringPool.MSG_ID, msgId);
        log.debug("\n Start msgId:{}  ReactiveRequestContextFilter", msgId);
        return chain.filter(exchange).subscriberContext(ctx -> ReactiveRequestContextHolder.put(ctx, request));
    }
}
