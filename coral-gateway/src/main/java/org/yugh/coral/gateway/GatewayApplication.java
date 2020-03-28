package org.yugh.coral.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.yugh.coral.gateway.filter.GatewayDataWorksFilter;
import org.yugh.coral.gateway.config.GatewayKeyResolver;

@EnableFeignClients
// @ComponentScan(basePackages = {"org.yugh.coral"})
@EnableDiscoveryClient
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class GatewayApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(GatewayApplication.class, args);
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
