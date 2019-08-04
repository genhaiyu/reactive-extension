package org.yugh.domainevent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.yugh.domainevent.boot.daos.impl.ExtendedRepositoryImpl;

/**
 * 驱动事件测试服务
 *
 * 基于 baeldung tutorials 学习修改
 *
 * @author yugenhai
 */
@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = ExtendedRepositoryImpl.class)
public class DomainEventApplication {

	public static void main(String[] args) {
		SpringApplication.run(DomainEventApplication.class, args);
	}

}
