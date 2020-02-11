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

@EnableWebFlux
@EnableFeignClients
@ComponentScan(basePackages = {"org.yugh.coral"})
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class DataWorksGatewayApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(DataWorksGatewayApplication.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
