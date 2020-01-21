/*
 * Copyright (c) 2019-2029, yugenhai108@gmail.com.
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

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.yugh.coral.core.pojo.bo.RequestHeaderBO;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

/**
 * @author yugenhai
 */
public class CustomRequestContextHolder {

    private static final Class<ServerHttpRequest> SERVER_HTTP_REQUEST_CLASS = ServerHttpRequest.class;
    private static final Class<RequestHeaderBO> REQUEST_HEADER_BO_CLASS = RequestHeaderBO.class;

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
     * Gets the {@code Mono<RequestHeaderBO>} from Reactor {@link Context}
     *
     * @return the Mono<RequestHeaderBO>
     */
    public static Mono<RequestHeaderBO> getRequestHeaderReactor() {
        return Mono.subscriberContext()
                .map(ctx -> ctx.get(REQUEST_HEADER_BO_CLASS));
    }

    /**
     * Put the {@code Mono<RequestHeaderBO>} to Reactor {@link Context}
     *
     * @param context
     * @param requestHeaderBO
     * @return the Reactor {@link Context}
     */
    public static Context setRequestHeaderReactor(Context context, RequestHeaderBO requestHeaderBO) {
        return context.put(REQUEST_HEADER_BO_CLASS, requestHeaderBO);
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
