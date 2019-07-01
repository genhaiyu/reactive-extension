package org.yugh.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.yugh.gateway.filter.PreAuthFilter;

/**
 * //网关服务
 *
 * @author: 余根海
 * @creation: 2019-07-01 11:47
 * @Copyright © 2019 yugenhai. All rights reserved.
 */
@EnableZuulProxy
@EnableFeignClients
@SpringCloudApplication
@ComponentScan(basePackages = "org.yugh")
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

	/**
	 * 初始化路由
	 * @author: 余根海
	 * @creation: 2019-06-26 17:50
	 * @return
	 */
	@Bean
	public PreAuthFilter authFilter(){
		return new PreAuthFilter();
	}

}
