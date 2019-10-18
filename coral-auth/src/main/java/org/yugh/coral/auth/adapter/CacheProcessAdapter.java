package org.yugh.coral.auth.adapter;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.data.redis.core.*;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.yugh.coral.auth.base.AbstractCacheHandler;
import org.yugh.coral.auth.cache.CacheKey;
import org.yugh.coral.auth.util.CollectionUtil;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * 缓存适配器
 *
 * @author yugenhai
 */
@Slf4j
@Getter
@Component
@RequiredArgsConstructor
public class CacheProcessAdapter extends AbstractCacheHandler implements SmartInitializingSingleton {

    private ValueOperations valueOps;
    private HashOperations hashOps;
    private ListOperations listOps;
    private SetOperations setOps;
    private ZSetOperations zSetOps;
    private final StringRedisTemplate redisTemplate;


    @Override
    public Object loadCache(String id) {
        return this.get(id);
    }

    @Override
    public Object getObjectByCache(String key) {
        try {
            long begin = System.currentTimeMillis();
            log.info("Get Guava Cache ...");
            Object obj = cache.get(key);
            long end = System.currentTimeMillis();
            log.info("Get Guava Cache Take Time : {}", (end - begin));
            return obj;
        } catch (Exception e) {
            log.error("Get Guava Cache Exception", e);
        }
        return null;
    }

    @Override
    public void removeCacheKey(String key) {
        this.cache.invalidate(key);
    }


    @Override
    public void set(CacheKey cacheKey, Object value) {
        String key = cacheKey.getKey();
        Duration expire = cacheKey.getExpire();
        if (expire == null) {
            set(key, value);
        } else {
            setEx(key, value, expire);
        }
    }

    @Override
    public void set(String key, Object value) {
        valueOps.set(key, value);
    }

    @Override
    public void setEx(String key, Object value, Duration timeout) {
        valueOps.set(key, value, timeout);
    }

    @Override
    public void setEx(String key, Object value, Long seconds) {
        valueOps.set(key, value, seconds, TimeUnit.SECONDS);
    }

    @Override
    public <T> T get(String key) {
        return (T) valueOps.get(key);
    }

    @Override
    public <T> T get(String key, Supplier<T> loader) {
        T value = this.get(key);
        if (value != null) {
            return value;
        }
        value = loader.get();
        if (value == null) {
            return null;
        }
        this.set(key, value);
        return value;
    }

    @Override
    public <T> T get(CacheKey cacheKey) {
        return (T) valueOps.get(cacheKey.getKey());
    }

    @Override
    @Nullable
    public <T> T get(CacheKey cacheKey, Supplier<T> loader) {
        String key = cacheKey.getKey();
        T value = this.get(key);
        if (value != null) {
            return value;
        }
        value = loader.get();
        if (value == null) {
            return null;
        }
        this.set(cacheKey, value);
        return value;
    }

    @Override
    public Boolean del(String key) {
        return redisTemplate.delete(key);
    }

    @Override
    public Boolean del(CacheKey key) {
        return redisTemplate.delete(key.getKey());
    }

    @Override
    public Long del(String... keys) {
        return del(Arrays.asList(keys));
    }

    @Override
    public Long del(Collection<String> keys) {
        return redisTemplate.delete(keys);
    }

    @Override
    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    @Override
    public void mSet(Object... keysValues) {
        valueOps.multiSet(CollectionUtil.toMap(keysValues));
    }

    @Override
    public List<Object> mGet(String... keys) {
        return mGet(Arrays.asList(keys));
    }

    @Override
    public List<Object> mGet(Collection<String> keys) {
        return valueOps.multiGet(keys);
    }

    @Override
    public Long decr(String key) {
        return valueOps.decrement(key);
    }

    @Override
    public Long decrBy(String key, long longValue) {
        return valueOps.decrement(key, longValue);
    }

    @Override
    public Long incr(String key) {
        return valueOps.increment(key);
    }

    @Override
    public Long incrBy(String key, long longValue) {
        return valueOps.increment(key, longValue);
    }

    @Override
    public Long getCounter(String key) {
        return Long.valueOf(String.valueOf(valueOps.get(key)));
    }

    @Override
    public Boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    @Override
    public Object randomKey() {
        return redisTemplate.randomKey();
    }

    @Override
    public void rename(String oldkey, String newkey) {
        redisTemplate.rename(oldkey, newkey);
    }

    @Override
    public Boolean move(String key, int dbIndex) {
        return redisTemplate.move(key, dbIndex);
    }

    @Override
    public Boolean expire(String key, long seconds) {
        return redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
    }

    @Override
    public Boolean expire(String key, Duration timeout) {
        return expire(key, timeout.getSeconds());
    }

    @Override
    public Boolean expireAt(String key, Date date) {
        return redisTemplate.expireAt(key, date);
    }

    @Override
    public Boolean expireAt(String key, long unixTime) {
        return expireAt(key, new Date(unixTime));
    }

    @Override
    public Boolean pexpire(String key, long milliseconds) {
        return redisTemplate.expire(key, milliseconds, TimeUnit.MILLISECONDS);
    }

    @Override
    public <T> T getSet(String key, Object value) {
        return (T) valueOps.getAndSet(key, value);
    }

    @Override
    public Boolean persist(String key) {
        return redisTemplate.persist(key);
    }

    @Override
    public String type(String key) {
        return redisTemplate.type(key).code();
    }

    @Override
    public Long ttl(String key) {
        return redisTemplate.getExpire(key);
    }

    @Override
    public Long pttl(String key) {
        return redisTemplate.getExpire(key, TimeUnit.MILLISECONDS);
    }

    @Override
    public void hSet(String key, Object field, Object value) {
        hashOps.put(key, field, value);
    }

    @Override
    public void hMset(String key, Map<Object, Object> hash) {
        hashOps.putAll(key, hash);
    }

    @Override
    public <T> T hGet(String key, Object field) {
        return (T) hashOps.get(key, field);
    }

    @Override
    public List hmGet(String key, Object... fields) {
        return hmGet(key, Arrays.asList(fields));
    }

    @Override
    public List hmGet(String key, Collection<Object> hashKeys) {
        return hashOps.multiGet(key, hashKeys);
    }

    @Override
    public Long hDel(String key, Object... fields) {
        return hashOps.delete(key, fields);
    }

    @Override
    public Boolean hExists(String key, Object field) {
        return hashOps.hasKey(key, field);
    }

    @Override
    public Map hGetAll(String key) {
        return hashOps.entries(key);
    }

    @Override
    public List hVals(String key) {
        return hashOps.values(key);
    }

    @Override
    public Set<Object> hKeys(String key) {
        return hashOps.keys(key);
    }

    @Override
    public Long hLen(String key) {
        return hashOps.size(key);
    }

    @Override
    public Long hIncrBy(String key, Object field, long value) {
        return hashOps.increment(key, field, value);
    }

    @Override
    public Double hIncrByFloat(String key, Object field, double value) {
        return hashOps.increment(key, field, value);
    }

    @Override
    public <T> T lIndex(String key, long index) {
        return (T) listOps.index(key, index);
    }

    @Override
    public Long lLen(String key) {
        return listOps.size(key);
    }

    @Override
    public <T> T lPop(String key) {
        return (T) listOps.leftPop(key);
    }

    @Override
    public Long lPush(String key, Object... values) {
        return listOps.leftPush(key, values);
    }

    @Override
    public void lSet(String key, long index, Object value) {
        listOps.set(key, index, value);
    }

    @Override
    public Long lRem(String key, long count, Object value) {
        return listOps.remove(key, count, value);
    }

    @Override
    public List lRange(String key, long start, long end) {
        return listOps.range(key, start, end);
    }

    @Override
    public void lTrim(String key, long start, long end) {
        listOps.trim(key, start, end);
    }

    @Override
    public <T> T rPop(String key) {
        return (T) listOps.rightPop(key);
    }

    @Override
    public Long rPush(String key, Object... values) {
        return listOps.rightPush(key, values);
    }

    @Override
    public <T> T rPopLPush(String srcKey, String dstKey) {
        return (T) listOps.rightPopAndLeftPush(srcKey, dstKey);
    }

    @Override
    public Long sAdd(String key, Object... members) {
        return setOps.add(key, members);
    }

    @Override
    public <T> T sPop(String key) {
        return (T) setOps.pop(key);
    }

    @Override
    public Set sMembers(String key) {
        return setOps.members(key);
    }

    @Override
    public boolean sIsMember(String key, Object member) {
        return setOps.isMember(key, member);
    }

    @Override
    public Set sInter(String key, String otherKey) {
        return setOps.intersect(key, otherKey);
    }

    @Override
    public Set sInter(String key, Collection<String> otherKeys) {
        return setOps.intersect(key, otherKeys);
    }

    @Override
    public <T> T sRandMember(String key) {
        return (T) setOps.randomMember(key);
    }

    @Override
    public List sRandMember(String key, int count) {
        return setOps.randomMembers(key, count);
    }

    @Override
    public Long sRem(String key, Object... members) {
        return setOps.remove(key, members);
    }

    @Override
    public Set sUnion(String key, String otherKey) {
        return setOps.union(key, otherKey);
    }

    @Override
    public Set sUnion(String key, Collection<String> otherKeys) {
        return setOps.union(key, otherKeys);
    }

    @Override
    public Set sDiff(String key, String otherKey) {
        return setOps.difference(key, otherKey);
    }

    @Override
    public Set sDiff(String key, Collection<String> otherKeys) {
        return setOps.difference(key, otherKeys);
    }

    @Override
    public Double zScore(String key, Object member) {
        return zSetOps.score(key, member);
    }

    @Override
    public Boolean zAdd(String key, Object member, double score) {
        return zSetOps.add(key, member, score);
    }

    @Override
    public Long zAdd(String key, Map<Object, Double> scoreMembers) {
        Set<ZSetOperations.TypedTuple<Object>> tuples = new HashSet<>();
        scoreMembers.forEach((k, v) -> {
            tuples.add(new DefaultTypedTuple<>(k, v));
        });
        return zSetOps.add(key, tuples);
    }

    @Override
    public Long zCard(String key) {
        return zSetOps.zCard(key);
    }

    @Override
    public Long zCount(String key, double min, double max) {
        return zSetOps.count(key, min, max);
    }

    @Override
    public Double zIncrBy(String key, Object member, double score) {
        return zSetOps.incrementScore(key, member, score);
    }

    @Override
    public Set zRange(String key, long start, long end) {
        return zSetOps.range(key, start, end);
    }

    @Override
    public Set zRevrange(String key, long start, long end) {
        return zSetOps.reverseRange(key, start, end);
    }

    @Override
    public Set zRangeByScore(String key, double min, double max) {
        return zSetOps.rangeByScore(key, min, max);
    }

    @Override
    public Long zRank(String key, Object member) {
        return zSetOps.rank(key, member);
    }

    @Override
    public Long zRevrank(String key, Object member) {
        return zSetOps.reverseRank(key, member);
    }

    @Override
    public Long zRem(String key, Object... members) {
        return zSetOps.remove(key, members);
    }

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
