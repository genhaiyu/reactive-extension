package org.yugh.gatewaynew;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.yugh.gatewaynew.config.GatewayKeyResolver;
import org.yugh.gatewaynew.filter.GatewayFilter;

/**
 * //网关服务
 *
 * @author 余根海
 * @creation 2019-07-01 11:47
 * @Copyright © 2019 yugenhai. All rights reserved.
 */
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "org.yugh")
@ComponentScan(basePackages = {"org.yugh.*"})
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class GatewayNewApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayNewApplication.class, args);
    }


    @Bean(name = "gatewayKeyResolver")
    public GatewayKeyResolver gatewayKeyResolver() {
        return new GatewayKeyResolver();
    }

    @Bean
    public GatewayFilter gatewayFilter() {
        return new GatewayFilter();
    }
}
