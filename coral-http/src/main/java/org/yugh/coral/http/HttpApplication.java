package org.yugh.coral.http;

import org.springframework.boot.SpringApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * 1. Jetty Reactive Streams HttpClient
 * 2. Spring WebFlux
 * 3. WebClient
 * 4. SpringCloud ...
 *
 * @author yugenhai
 */
@EnableCaching
@SpringCloudApplication
public class HttpApplication {

	public static void main(String[] args) {
		SpringApplication.run(HttpApplication.class, args);
	}
}
