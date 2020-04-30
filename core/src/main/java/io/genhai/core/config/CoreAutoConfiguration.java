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

package io.genhai.core.config;

import io.genhai.core.cache.RedisOperationProperties;
import io.genhai.core.cache.RedisProcessSupport;
import io.genhai.core.config.distribute.DistributeRequestProperties;
import io.genhai.core.config.distribute.DistributeRequestProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author yugenhai
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({DistributeRequestProperties.class, RedisOperationProperties.class,
        ReactiveProperties.class, JettyProperties.class})
public class CoreAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public DistributeRequestProvider distributeRequestProvider(DistributeRequestProperties distributeRequestProperties) {
        return new DistributeRequestProvider(distributeRequestProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public RedisProcessSupport redisProcessSupport(RedisOperationProperties redisOperationProperties, RedisTemplate<String, Object> redisTemplate) {
        return new RedisProcessSupport(redisOperationProperties, redisTemplate);
    }
}
