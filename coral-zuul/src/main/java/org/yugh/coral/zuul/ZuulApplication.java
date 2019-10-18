package org.yugh.coral.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.yugh.coral.zuul.filter.PreAuthFilter;

/**
 * //Spring Cloud 体系第一代 zuul 路由器
 * 现迁移到 coral-gateway
 *
 * @author: 余根海
 * @creation: 2019-07-01 11:47
 * @Copyright © 2019 yugenhai. All rights reserved.
 */
@EnableZuulProxy
@EnableFeignClients
@SpringCloudApplication
@ComponentScan(basePackages = "org.yugh.coral")
public class ZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZuulApplication.class, args);
    }

    @Bean
    public PreAuthFilter authFilter() {
        return new PreAuthFilter();
    }

}
