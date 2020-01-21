package org.yugh.coral.core.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.yugh.coral.core.config.cache.CoreRedisConfig;
import org.yugh.coral.core.config.distribute.SimpleDistributeConfig;

/**
 * @author yugenhai
 */
@Configuration
public class CoreContextConfig {

    @Bean
    @ConditionalOnMissingBean(CoreRedisConfig.class)
    public CoreRedisConfig simpleRedisConfig() {
        return new CoreRedisConfig();
    }

    @Bean
    @ConditionalOnMissingBean(SimpleDistributeConfig.class)
    public SimpleDistributeConfig simpleDistributeConfig() {
        return new SimpleDistributeConfig();
    }

//    @Override
//    public KeyGenerator keyGenerator() {
//        return new CustomKeyGenerator();
//    }
}
