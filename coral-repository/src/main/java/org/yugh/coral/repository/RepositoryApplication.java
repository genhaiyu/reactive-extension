package org.yugh.coral.repository;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Spring Data JPA 项目
 * <p>
 * 不用数据需要排除 (exclude = DataSourceAutoConfiguration.class)
 *
 * @author genhai yu
 */
@SpringBootApplication
@EntityScan("org.yugh.coral")
@ComponentScan(value = "org.yugh.coral")
@EnableJpaRepositories(basePackages = {"org.yugh.coral"})
public class RepositoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(RepositoryApplication.class, args);
    }

}
