package org.yugh.coral.zuul.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.yugh.coral.core.config.cache.CoreRedisConfig;

/**
 * @author yugenhai
 */
@Getter
@Setter
@Component
public class RedisCacheConfig extends CoreRedisConfig {

    @Value("${spring.redis.expire}")
    private int expire;
    @Value("${spring.redis.key-prefix}")
    private String keyPrefix;
    @Value("${spring.redis.open}")
    private boolean open;

    @Override
    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

    @Override
    public void setExpire(int expire) {
        this.expire = expire;
    }
}
