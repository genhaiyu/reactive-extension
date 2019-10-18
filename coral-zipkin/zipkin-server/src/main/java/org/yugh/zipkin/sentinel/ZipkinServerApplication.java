package org.yugh.zipkin.sentinel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import zipkin2.server.internal.EnableZipkinServer;

/**
 * zipkin服务 先启动zipkin server,再启动zipkin response -> zipkin request
 * 请求方式是zipkin request  -> zipkin response
 *
 * @author genhai yu
 */
@EnableZipkinServer
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class ZipkinServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZipkinServerApplication.class, args);
	}

}
