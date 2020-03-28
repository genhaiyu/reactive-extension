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
 */


package org.yugh.coral.boot.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.Nullable;

import java.io.IOException;

/**
 * @author yugenhai
 */
public class HttpRequestInterceptor implements ClientHttpRequestInterceptor {

    private static final Logger LOG = LoggerFactory.getLogger(HttpRequestInterceptor.class);

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
        LOG.info("\n Request Headers: {}", request.getHeaders());
        LOG.info("\n Request Method: {}", request.getMethod());
        LOG.info("\n Request URI: {}", request.getURI());
    }
}
