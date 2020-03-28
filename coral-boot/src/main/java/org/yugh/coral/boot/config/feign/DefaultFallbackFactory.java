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

import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import java.lang.reflect.ParameterizedType;

/**
 * Load on initialization for Feign interface
 *
 * @author yugenhai
 */
public class DefaultFallbackFactory<T> implements FallbackFactory<T>, InitializingBean {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultFallbackFactory.class);

    private Class<T> clazz;
    private T obj;

    @Override
    public T create(Throwable throwable) {
        LOG.error("Feign exception className : {}, Throwable message : {} ", this.clazz.getSimpleName(), throwable.getMessage());
        return obj;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void afterPropertiesSet() throws Exception {
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        clazz = (Class<T>) type.getActualTypeArguments()[0];
        obj = new FeignProxy<>(clazz).getProxy();
    }
}

