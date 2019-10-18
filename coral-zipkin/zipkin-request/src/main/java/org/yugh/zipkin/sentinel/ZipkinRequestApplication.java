package org.yugh.zipkin.sentinel;

import brave.sampler.Sampler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
/**
 * zipkin服务 先启动zipkin server,再启动zipkin response -> zipkin request
 * 请求方式是zipkin request  -> zipkin response
 *
 * @author genhai yu
 */
@EnableFeignClients
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class ZipkinRequestApplication {


    public static void main(String[] args) {
        SpringApplication.run(ZipkinRequestApplication.class, args);
    }


    @Bean
    public Sampler defaultSampler() {
        return Sampler.ALWAYS_SAMPLE;
    }


}
