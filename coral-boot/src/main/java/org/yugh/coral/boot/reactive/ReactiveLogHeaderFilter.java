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

import org.slf4j.MDC;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import org.yugh.coral.core.config.RequestHeaderProvider;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import java.util.Map;

import static java.util.stream.Collectors.toMap;

/**
 * Log Header Reactive application.
 *
 * @author yugenhai
 */
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
public class ReactiveLogHeaderFilter implements WebFilter, Ordered {

    // custom order, every filter should be set
    private int order = Ordered.HIGHEST_PRECEDENCE + 2;

    @Override
    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

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
            headers.keySet().forEach(MDC::remove);
        }).then();
    }
}