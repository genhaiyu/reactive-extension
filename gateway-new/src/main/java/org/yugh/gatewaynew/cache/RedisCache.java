package org.yugh.gatewaynew.cache;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.data.redis.core.*;
import org.springframework.util.Assert;

/**
 * @author yugenhai
 */
@Getter
@RequiredArgsConstructor
public class RedisCache implements SmartInitializingSingleton {

    private final RedisTemplate<String, Object> redisTemplate;
    private ValueOperations<String, Object> valueOps;
    private HashOperations<String, Object, Object> hashOps;
    private ListOperations<String, Object> listOps;
    private SetOperations<String, Object> setOps;
    private ZSetOperations<String, Object> zSetOps;


    @Override
    public void afterSingletonsInstantiated() {
        Assert.notNull(redisTemplate, "redisTemplate is null");
        valueOps = redisTemplate.opsForValue();
        hashOps = redisTemplate.opsForHash();
        listOps = redisTemplate.opsForList();
        setOps = redisTemplate.opsForSet();
        zSetOps = redisTemplate.opsForZSet();
    }
}
