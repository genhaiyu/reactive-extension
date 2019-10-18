package org.yugh.coral.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.yugh.coral.gateway.filter.GatewayDataWorksFilter;
import org.yugh.coral.gateway.reactor.GatewayKeyResolver;

/**
 * DataWorks Gateway
 * <p>
 * Use Spring Cloud Gateway
 * <p>
 * This Application used HTTP 5.0 and Reactor By Webflux
 *
 * @author 余根海
 * @creation 2019-07-01 11:47
 * @Copyright © 2019 yugenhai. All rights reserved.
 */
@EnableWebFlux
@EnableFeignClients
@ComponentScan(basePackages = {"org.yugh.coral"})
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class DataWorksGatewayApplication {

    /**
     * @param args
     * @EnableHystrix
     */
    public static void main(String[] args) {
        SpringApplication.run(DataWorksGatewayApplication.class, args);
    }

    @Bean(name = "gatewayKeyResolver")
    public GatewayKeyResolver gatewayKeyResolver() {
        return new GatewayKeyResolver();
    }

    @Bean
    public GatewayDataWorksFilter gatewayFilter() {
        return new GatewayDataWorksFilter();
    }

}
