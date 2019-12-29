package org.yugh.coral.boot.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.yugh.coral.boot.reactive.ReactiveRequestContextFilter;
import org.yugh.coral.boot.rest.CustomRestTemplateCustomizer;
import org.yugh.coral.boot.servlet.ServletContextRequestListener;

/**
 * @author yugenhai
 */
@Configuration
public class BootContextConfig {

    @Bean
    @ConditionalOnMissingBean(ServletContextRequestListener.class)
    public ServletContextRequestListener servletContextRequestListener() {
        return new ServletContextRequestListener();
    }

    @Bean
    @ConditionalOnMissingBean(ReactiveRequestContextFilter.class)
    public ReactiveRequestContextFilter reactiveRequestContextFilter() {
        return new ReactiveRequestContextFilter();
    }

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

}
