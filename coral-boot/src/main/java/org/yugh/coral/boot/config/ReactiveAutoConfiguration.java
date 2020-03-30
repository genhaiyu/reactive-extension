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


package org.yugh.coral.boot.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.yugh.coral.boot.config.jetty.GracefulShutdownJettyConnectorCustomizer;
import org.yugh.coral.boot.config.jetty.GracefulShutdownJettyContainerCustomizer;
import org.yugh.coral.boot.config.jetty.GracefulShutdownJettyHealthIndicator;
import org.yugh.coral.boot.config.jetty.GracefulShutdownJettyProperties;
import org.yugh.coral.boot.reactive.ReactiveLogHeaderFilter;
import org.yugh.coral.boot.reactive.ReactiveRequestContextFilter;
import org.yugh.coral.boot.rest.CustomRestTemplateCustomizer;
import org.yugh.coral.boot.servlet.ServletRequestContextListener;
import org.yugh.coral.core.config.DispatcherRequestCustomizer;
import org.yugh.coral.core.config.RequestAdapterProvider;

/**
 * @author yugenhai
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({GracefulShutdownJettyProperties.class})
public class ReactiveAutoConfiguration {

    @Bean
    @Qualifier("customRestTemplateCustomizer")
    public CustomRestTemplateCustomizer customRestTemplateCustomizer() {
        return new CustomRestTemplateCustomizer();
    }

    @Bean
    @DependsOn(value = {"customRestTemplateCustomizer"})
    public RestTemplateBuilder restTemplateBuilder() {
        return new RestTemplateBuilder(customRestTemplateCustomizer());
    }

    @Bean
    @ConditionalOnMissingBean
    public GracefulShutdownJettyHealthIndicator gracefulShutdownJettyHealthIndicator(ApplicationContext ctx, GracefulShutdownJettyProperties properties) {
        return new GracefulShutdownJettyHealthIndicator(ctx, properties);
    }

    @Bean
    @ConditionalOnMissingBean
    public GracefulShutdownJettyContainerCustomizer gracefulShutdownJettyContainerCustomizer(GracefulShutdownJettyConnectorCustomizer gracefulShutdownJettyConnectorCustomizer) {
        return new GracefulShutdownJettyContainerCustomizer(gracefulShutdownJettyConnectorCustomizer);
    }

    @Bean
    @ConditionalOnMissingBean
    public GracefulShutdownJettyConnectorCustomizer gracefulShutdownJettyConnectorCustomizer(ApplicationContext applicationContext, GracefulShutdownJettyProperties properties) {
        return new GracefulShutdownJettyConnectorCustomizer(applicationContext, properties);
    }

    @Bean
    @ConditionalOnMissingBean
    public ReactiveLogHeaderFilter reactiveLogHeaderFilter() {
        return new ReactiveLogHeaderFilter();
    }

    @Bean
    @ConditionalOnMissingBean
    public ReactiveRequestContextFilter reactiveRequestContextFilter(DispatcherRequestCustomizer<RequestAdapterProvider.ProduceValues> dispatcherRequestCustomizer) {
        return new ReactiveRequestContextFilter(dispatcherRequestCustomizer);
    }

    @Bean
    @ConditionalOnMissingBean
    public ServletRequestContextListener servletRequestContextListener(DispatcherRequestCustomizer<RequestAdapterProvider.ProduceValues> dispatcherRequestCustomizer) {
        return new ServletRequestContextListener(dispatcherRequestCustomizer);
    }
}
