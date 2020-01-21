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
package org.yugh.coral.boot.rest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.Nullable;

import java.io.IOException;

/**
 * @author yugenhai
 */
@Slf4j
public class CustomClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {

    @Override
    @Nullable
    public ClientHttpResponse intercept(@Nullable HttpRequest request, @Nullable byte[] body, @Nullable ClientHttpRequestExecution execution)
            throws IOException {
        logRequestDetails(request);
        return execution.execute(request, body);
    }

    /**
     * Request info
     *
     * @param request
     */
    private void logRequestDetails(HttpRequest request) {
        log.info("\n Request Headers: {}", request.getHeaders());
        log.info("\n Request Method: {}", request.getMethod());
        log.info("\n Request URI: {}", request.getURI());
    }
}
