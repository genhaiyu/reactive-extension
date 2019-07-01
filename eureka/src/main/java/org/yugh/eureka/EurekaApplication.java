package org.yugh.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * //
 * 1:注册中心
 *
 * @author: 余根海
 * @creation: 2019-06-24 14:34
 * @Copyright © 2019 yugenhai. All rights reserved.
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaApplication.class, args);
	}

}
