/*
 * Copyright (c) 2014-2020, yugenhai108@gmail.com.
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.gnu.org/licenses/lgpl.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.yugh.coral.boot.reactive;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import org.yugh.coral.core.common.constant.LogMessageInfo;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import java.util.Map;

import static java.util.stream.Collectors.toMap;

/**
 * @author yugenhai
 */
@Slf4j
@Order(Integer.MIN_VALUE)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
public class LogHeaderFilter implements WebFilter {


    @Nullable
    @Override
    public Mono<Void> filter(@Nullable ServerWebExchange ex, @Nullable WebFilterChain chain) {
        ex.getResponse().beforeCommit(
                () -> addContextToHttpResponseHeaders(ex.getResponse())
        );
        return chain.filter(ex)
                .subscriberContext(
                        ctx -> addRequestHeadersToContext(ex.getRequest(), ctx)
                );
    }


    private Context addRequestHeadersToContext(final ServerHttpRequest request, final Context context) {
        MDC.put(LogMessageInfo.REQUEST_ID_KEY, request.getHeaders().toSingleValueMap().get(LogMessageInfo.REQUEST_ID_KEY));
        final Map<String, String> contextMap = request.getHeaders().toSingleValueMap().entrySet()
                .stream()
                .filter(req -> req.getKey().startsWith(LogMessageInfo.REQUEST_ID_KEY))
                .collect(toMap(
                        v -> v.getKey().substring(LogMessageInfo.REQUEST_ID_KEY.length()), Map.Entry::getValue));
        return context.put(LogMessageInfo.CONTEXT_MAP, contextMap);
    }

    private Mono<Void> addContextToHttpResponseHeaders(final ServerHttpResponse res) {
        return Mono.subscriberContext().doOnNext(ctx -> {
            if (!ctx.hasKey(LogMessageInfo.CONTEXT_MAP)) {
                return;
            }
            final HttpHeaders headers = res.getHeaders();
            ctx.<Map<String, String>>get(LogMessageInfo.CONTEXT_MAP).forEach(
                    (key, value) -> headers.add(LogMessageInfo.REQUEST_ID_KEY + key, value)
            );
            headers.keySet().forEach(MDC::remove);
        }).then();
    }
}