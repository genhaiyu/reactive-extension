package org.yugh.coral.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.SpringCloudApplication;
import org.yugh.coral.client.properties.ApplicationProperties;

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
@EnableConfigurationProperties(ApplicationProperties.class)
public class ClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientApplication.class, args);
	}
}
