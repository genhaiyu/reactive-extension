package org.yugh.globalauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.context.request.RequestContextListener;

/**
 * 全局鉴权拦截组件
 *
 * @author yugenhai
 */
@ComponentScan(basePackages = {"org.yugh"})
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class GlobalAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(GlobalAuthApplication.class, args);
    }

    /**
     * RequestContextListener监听器
     *
     * @return
     */
    @Bean
    public RequestContextListener requestContextListenerBean() {
        return new RequestContextListener();
    }

}
