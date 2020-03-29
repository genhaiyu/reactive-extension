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

package org.yugh.coral.core.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.yugh.coral.core.cache.RedisOperationProperties;
import org.yugh.coral.core.cache.RedisProcessSupport;
import org.yugh.coral.core.config.distribute.DistributeRequestProperties;
import org.yugh.coral.core.config.distribute.DistributeRequestProvider;

/**
 * @author yugenhai
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({CoralJettyProperties.class, CoralJettyValueProperties.class,
        DistributeRequestProperties.class, RedisOperationProperties.class, CoralReactiveProperties.class})
public class CoralCoreAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public DistributeRequestProvider distributeRequestCustomizer(DistributeRequestProperties distributeRequestProperties) {
        return new DistributeRequestProvider(distributeRequestProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public RedisProcessSupport redisProcessSupport(RedisOperationProperties redisOperationProperties, RedisTemplate<String, Object> redisTemplate) {
        return new RedisProcessSupport(redisOperationProperties, redisTemplate);
    }
}
