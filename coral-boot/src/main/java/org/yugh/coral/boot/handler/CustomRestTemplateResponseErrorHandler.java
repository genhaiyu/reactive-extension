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
package org.yugh.coral.boot.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import org.yugh.coral.core.config.properties.JsonUtils;

import java.io.IOException;
import java.net.URI;

/**
 * @author yugenhai
 */
@Slf4j
public class CustomRestTemplateResponseErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
        HttpStatus status = clientHttpResponse.getStatusCode();
        return status.is4xxClientError() || status.is5xxServerError();
    }

    @Override
    public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {
        String responseAsString = JsonUtils.serializeToJson(clientHttpResponse.getBody());
        log.error("handleError: {}", responseAsString);
        throw new CustomException(responseAsString);
    }

    @Override
    public void handleError(URI url, HttpMethod method, ClientHttpResponse clientHttpResponse) throws IOException {
        String responseAsString = JsonUtils.serializeToJson(clientHttpResponse.getBody());
        log.error("handleError URL: {}, HttpMethod: {}, ResponseBody: {}", url, method, responseAsString);
        throw new CustomException(responseAsString);
    }

    static class CustomException extends IOException {
        public CustomException(String message) {
            super(message);
        }
    }
}
