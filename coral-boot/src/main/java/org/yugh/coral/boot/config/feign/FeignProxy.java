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


package org.yugh.coral.boot.config.feign;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Feign Proxy, see {@link DefaultFallbackFactory}
 *
 * @author yugenhai
 */
public class FeignProxy<T> implements InvocationHandler {

    private static final Logger LOG = LoggerFactory.getLogger(FeignProxy.class);

    private final static String DEFAULT_METHOD = "toString";
    private Class<T> clazz;

    public FeignProxy(Class<T> clazz) {
        this.clazz = clazz;
    }

    public T getProxy() {
        return clazz.cast(Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, this));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (DEFAULT_METHOD.equals(method.getName())) {
            return clazz.getName();
        }
        LOG.error("Feign exception class name : {}, Method name : {} ", clazz.getSimpleName(), method.getName());
        return new FeignErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private static class FeignErrorResponse {
        private HttpStatus httpStatus;

        public FeignErrorResponse(HttpStatus httpStatus) {
            this.httpStatus = httpStatus;
        }

        public HttpStatus getHttpStatus() {
            return httpStatus;
        }

        public void setHttpStatus(HttpStatus httpStatus) {
            this.httpStatus = httpStatus;
        }
    }
}
