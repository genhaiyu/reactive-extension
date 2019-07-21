package org.yugh.gatewaynew;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.yugh.gatewaynew.config.GatewayKeyResolver;
import org.yugh.gatewaynew.filter.DataWorksGatewayFilter;

/**
 * //数据工厂网关服务
 *
 * @author 余根海
 * @creation 2019-07-01 11:47
 * @Copyright © 2019 yugenhai. All rights reserved.
 */
@EntityScan
@EnableFeignClients
@EnableDiscoveryClient
@ComponentScan(basePackages = {"*", "*"})
@EnableJpaRepositories(basePackages = {"*"})
@SpringBootApplication(exclude = {org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration.class})
public class GatewayNewApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayNewApplication.class, args);
    }


    @Bean(name = "gatewayKeyResolver")
    public GatewayKeyResolver gatewayKeyResolver() {
        return new GatewayKeyResolver();
    }

    @Bean
    public DataWorksGatewayFilter gatewayFilter() {
        return new DataWorksGatewayFilter();
    }
}
