package org.yugh.cqrs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * CQRS（Command Query Responsibility Segration）
 * 把CRUD系统拆分为两部分：命令（Command）处理和查询（Query）处理。
 * 其中命令处理包括增、删、改。
 * <p>
 * 然后命令与查询两边可以用不同的架构实现，以实现CQ两端（即Command Side，简称C端；Query Side，简称Q端）的分别优化。
 * 两边所涉及到的实体对象也可以不同，从而继续演变成下面这样。
 *
 * @author yugenhai
 */
@SpringBootApplication
@ComponentScan(basePackages = {"org.yugh"})
public class CqrsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CqrsApplication.class, args);
    }

}
