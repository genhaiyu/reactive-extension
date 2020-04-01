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


package org.yugh.coral.boot.servlet.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Feign Interceptor, For Servlet
 *
 * @author yugenhai
 */
@Deprecated
public class FeignRequestInterceptor implements RequestInterceptor {

    private final static String AUTHORIZATION = "AUTHORIZATION";

    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Assert.notNull(attributes, () -> "FeignRequestInterceptor '" + attributes + "' must not null");
        HttpServletRequest request = attributes.getRequest();
        String token = request.getHeader(AUTHORIZATION);
        requestTemplate.header(token);
    }
}
