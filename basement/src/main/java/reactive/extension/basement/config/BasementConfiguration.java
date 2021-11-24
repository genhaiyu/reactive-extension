package reactive.extension.basement.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import reactive.extension.basement.cache.SimpleRedisProcessor;
import reactive.extension.basement.cache.SimpleRedisProperties;
import reactive.extension.basement.config.distributor.DistributorRequestInitiation;
import reactive.extension.basement.config.distributor.DistributorRequestProperties;

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({DistributorRequestProperties.class, SimpleRedisProperties.class,
        ReactiveProperties.class, JettyProperties.class})
public class BasementConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public DistributorRequestInitiation distributorRequestInitiation(DistributorRequestProperties distributorRequestProperties) {
        return new DistributorRequestInitiation(distributorRequestProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public SimpleRedisProcessor simpleRedisProcessor(SimpleRedisProperties simpleRedisProperties, RedisTemplate<String, Object> redisTemplate) {
        return new SimpleRedisProcessor(simpleRedisProperties, redisTemplate);
    }
}
