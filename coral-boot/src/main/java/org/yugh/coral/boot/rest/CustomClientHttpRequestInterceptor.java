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
