package org.yugh.springbootadminserver;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@EnableAdminServer
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class SpringbootAdminServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootAdminServerApplication.class, args);
	}

}
