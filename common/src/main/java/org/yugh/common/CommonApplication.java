package org.yugh.common;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 临时common ,需要拆分到一个基础项目中被引用
 * <p>
 * 不用数据需要排除 (exclude = DataSourceAutoConfiguration.class)
 *
 * @author genhai yu
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class CommonApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommonApplication.class, args);
    }

}
