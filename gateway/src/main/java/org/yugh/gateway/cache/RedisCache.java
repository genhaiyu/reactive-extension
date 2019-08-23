/*
 * private final RedisTemplate redisTemplate;
 * private ReactiveSetOperations rSetOps;
 */
package org.yugh.gateway.cache;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.data.redis.core.*;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.yugh.auth.util.CollectionUtil;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * Redis Util
 *
 * @author yugenhai
 */
@Getter
@Component
@RequiredArgsConstructor
public class RedisCache implements SmartInitializingSingleton {

    private ValueOperations valueOps;
    private HashOperations hashOps;
    private ListOperations listOps;
    private SetOperations setOps;
    private ZSetOperations zSetOps;
    private final StringRedisTemplate redisTemplate;

    /**
     * Set Cache Key
     *
     * @param cacheKey
     * @param value
     */
    public void set(CacheKey cacheKey, Object value) {
        String key = cacheKey.getKey();
        Duration expire = cacheKey.getExpire();
        if (expire == null) {
            set(key, value);
        } else {
            setEx(key, value, expire);
        }
    }

    /**
     * Simple Set Key
     *
     * @param key
     * @param value
     */
    public void set(String key, Object value) {
        valueOps.set(key, value);
    }

    /**
     * Set key -> second
     * If key Exist = Override
     *
     * @param key
     * @param value
     * @param timeout
     */
    public void setEx(String key, Object value, Duration timeout) {
        valueOps.set(key, value, timeout);
    }

    /**
     * Set key -> second
     * If key Exist = Override
     *
     * @param key
     * @param value
     * @param seconds
     */
    public void setEx(String key, Object value, Long seconds) {
        valueOps.set(key, value, seconds, TimeUnit.SECONDS);
    }

    /**
     * Get key
     * <p>
     * Or nil
     *
     * @param key
     * @param <T>
     * @return
     */
    @Nullable
    public <T> T get(String key) {
        return (T) valueOps.get(key);
    }


    /**
     * Get key
     * <p>
     * If null -> loader
     *
     * @param key
     * @param loader
     * @param <T>
     * @return
     */
    @Nullable
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

    /**
     * Get Cache key
     * <p>
     * Or nil
     *
     * @param cacheKey
     * @param <T>
     * @return
     */
    @Nullable
    public <T> T get(CacheKey cacheKey) {
        return (T) valueOps.get(cacheKey.getKey());
    }


    /**
     * Get Cache key
     * <p>
     * If null -> loader
     *
     * @param cacheKey
     * @param loader
     * @param <T>
     * @return
     */
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

    /**
     * Del key
     *
     * @param key
     * @return
     */
    public Boolean del(String key) {
        return redisTemplate.delete(key);
    }

    /**
     * Del Cache key
     *
     * @param key
     * @return
     */
    public Boolean del(CacheKey key) {
        return redisTemplate.delete(key.getKey());
    }


    /**
     * Del keys
     *
     * @param keys
     * @return
     */
    public Long del(String... keys) {
        return del(Arrays.asList(keys));
    }

    /**
     * Del list key
     *
     * @param keys
     * @return
     */
    public Long del(Collection<String> keys) {
        return redisTemplate.delete(keys);
    }

    /**
     * Pattern key
     * <p>
     * KEYS * -> key 。
     * KEYS h?llo -> hello ， hallo or hxllo
     * KEYS h*llo -> hllo or heeeeello
     * KEYS h[ae]llo -> hello or hallo ，No Pattern  hillo 。
     * symbols \
     *
     * @param pattern
     * @return
     */
    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * Set keysValues
     *
     * @param keysValues
     */
    public void mSet(Object... keysValues) {
        valueOps.multiSet(CollectionUtil.toMap(keysValues));
    }

    /**
     * Return Keys
     * <p>
     * Or nil
     *
     * @param keys
     * @return
     */
    public List<Object> mGet(String... keys) {
        return mGet(Arrays.asList(keys));
    }

    /**
     * Return Keys
     * <p>
     * Or nil
     *
     * @param keys
     * @return
     */
    public List<Object> mGet(Collection<String> keys) {
        return valueOps.multiGet(keys);
    }

    /**
     * Decrement key
     *
     * @param key
     * @return
     */
    public Long decr(String key) {
        return valueOps.decrement(key);
    }


    /**
     * Decrement key
     *
     * @param key
     * @param longValue
     * @return
     */
    public Long decrBy(String key, long longValue) {
        return valueOps.decrement(key, longValue);
    }

    /**
     * Increment key
     *
     * @param key
     * @return
     */
    public Long incr(String key) {
        return valueOps.increment(key);
    }

    /**
     * Increment key
     *
     * @param key
     * @param longValue
     * @return
     */
    public Long incrBy(String key, long longValue) {
        return valueOps.increment(key, longValue);
    }

    /**
     * Get Count key
     *
     * @param key
     * @return
     */
    public Long getCounter(String key) {
        return Long.valueOf(String.valueOf(valueOps.get(key)));
    }

    /**
     * Exists key
     *
     * @param key
     * @return
     */
    public Boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * Get random key
     *
     * @return
     */
    public Object randomKey() {
        return redisTemplate.randomKey();
    }

    /**
     * Rename key to newKey
     * <p>
     * If Equals to Failed
     * <p>
     * If exists to Rename
     *
     * @param oldkey
     * @param newkey
     */
    public void rename(String oldkey, String newkey) {
        redisTemplate.rename(oldkey, newkey);
    }

    /**
     * Move key to Other db
     * <p>
     * If key NoExists it's Failed
     *
     * @param key
     * @param dbIndex
     * @return
     */
    public Boolean move(String key, int dbIndex) {
        return redisTemplate.move(key, dbIndex);
    }

    /**
     * Set key expire
     *
     * @param key
     * @param seconds
     * @return
     */
    public Boolean expire(String key, long seconds) {
        return redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
    }

    /**
     * Set key expire
     *
     * @param key
     * @param timeout
     * @return
     */
    public Boolean expire(String key, Duration timeout) {
        return expire(key, timeout.getSeconds());
    }

    /**
     * Set key expire To Date
     *
     * @param key
     * @param date
     * @return
     */
    public Boolean expireAt(String key, Date date) {
        return redisTemplate.expireAt(key, date);
    }

    /**
     * Set key to expire
     *
     * @param key
     * @param unixTime
     * @return
     */
    public Boolean expireAt(String key, long unixTime) {
        return expireAt(key, new Date(unixTime));
    }

    /**
     * Set key to expire By milliseconds
     *
     * @param key
     * @param milliseconds
     * @return
     */
    public Boolean pexpire(String key, long milliseconds) {
        return redisTemplate.expire(key, milliseconds, TimeUnit.MILLISECONDS);
    }

    /**
     * Set key to New Value ,return old value
     * <p>
     * If key NoExists it's Failed
     *
     * @param key
     * @param value
     * @param <T>
     * @return
     */
    public <T> T getSet(String key, Object value) {
        return (T) valueOps.getAndSet(key, value);
    }

    /**
     * Del Key's expire
     * <p>
     * Change Final
     *
     * @param key
     * @return
     */
    public Boolean persist(String key) {
        return redisTemplate.persist(key);
    }

    /**
     * Reurn key's Type
     *
     * @param key
     * @return
     */
    public String type(String key) {
        return redisTemplate.type(key).code();
    }

    /**
     * Get key time to live
     *
     * @param key
     * @return
     */
    public Long ttl(String key) {
        return redisTemplate.getExpire(key);
    }

    /**
     * Get key time to live  - MILLISECONDS
     *
     * @param key
     * @return
     */
    public Long pttl(String key) {
        return redisTemplate.getExpire(key, TimeUnit.MILLISECONDS);
    }

    /**
     * Set key field to Value
     *
     * @param key
     * @param field
     * @param value
     */
    public void hSet(String key, Object field, Object value) {
        hashOps.put(key, field, value);
    }

    /**
     * Set Map key-value to Hash
     *
     * @param key
     * @param hash
     */
    public void hMset(String key, Map<Object, Object> hash) {
        hashOps.putAll(key, hash);
    }

    /**
     * Get key to Field
     *
     * @param key
     * @param field
     * @param <T>
     * @return
     */
    public <T> T hGet(String key, Object field) {
        return (T) hashOps.get(key, field);
    }

    /**
     * Get key to Fields
     *
     * @param key
     * @param fields
     * @return
     */
    public List hmGet(String key, Object... fields) {
        return hmGet(key, Arrays.asList(fields));
    }

    /**
     * Get key to hashKeys
     *
     * @param key
     * @param hashKeys
     * @return
     */
    public List hmGet(String key, Collection<Object> hashKeys) {
        return hashOps.multiGet(key, hashKeys);
    }

    /**
     * Del key to fields
     *
     * @param key
     * @param fields
     * @return
     */
    public Long hDel(String key, Object... fields) {
        return hashOps.delete(key, fields);
    }

    /**
     * Get key to field - Exists
     *
     * @param key
     * @param field
     * @return
     */
    public Boolean hExists(String key, Object field) {
        return hashOps.hasKey(key, field);
    }

    /**
     * Return Key - field name
     *
     * @param key
     * @return
     */
    public Map hGetAll(String key) {
        return hashOps.entries(key);
    }

    /**
     * Return key All Hash
     *
     * @param key
     * @return
     */
    public List hVals(String key) {
        return hashOps.values(key);
    }

    /**
     * Get keys Hash , hfields
     * <p>
     * Set
     *
     * @param key
     * @return
     */
    public Set<Object> hKeys(String key) {
        return hashOps.keys(key);
    }

    /**
     * Get key Hash size
     *
     * @param key
     * @return
     */
    public Long hLen(String key) {
        return hashOps.size(key);
    }

    /**
     * Increment key to field
     *
     * @param key
     * @param field
     * @param value
     * @return
     */
    public Long hIncrBy(String key, Object field, long value) {
        return hashOps.increment(key, field, value);
    }

    /**
     * Increment key to field - float
     *
     * @param key
     * @param field
     * @param value
     * @return
     */
    public Double hIncrByFloat(String key, Object field, double value) {
        return hashOps.increment(key, field, value);
    }

    /**
     * Return key to Index
     *
     * @param key
     * @param index
     * @param <T>
     * @return
     */
    public <T> T lIndex(String key, long index) {
        return (T) listOps.index(key, index);
    }

    /**
     * Return key length
     * <p>
     * or 0
     *
     * @param key
     * @return
     */
    public Long lLen(String key) {
        return listOps.size(key);
    }

    /**
     * Remove key left
     *
     * @param key
     * @param <T>
     * @return
     */
    public <T> T lPop(String key) {
        return (T) listOps.leftPop(key);
    }

    /**
     * lPush
     *
     * @param key
     * @param values
     * @return
     */
    public Long lPush(String key, Object... values) {
        return listOps.leftPush(key, values);
    }

    /**
     * Set key to Index
     *
     * @param key
     * @param index
     * @param value
     */
    public void lSet(String key, long index, Object value) {
        listOps.set(key, index, value);
    }

    /**
     * Remove key to value
     * <p>
     * count > 0
     * count < 0
     * count = 0
     *
     * @param key
     * @param count
     * @param value
     * @return
     */
    public Long lRem(String key, long count, Object value) {
        return listOps.remove(key, count, value);
    }


    /**
     * Key range
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public List lRange(String key, long start, long end) {
        return listOps.range(key, start, end);
    }

    /**
     * Trim key
     *
     * @param key
     * @param start
     * @param end
     */
    public void lTrim(String key, long start, long end) {
        listOps.trim(key, start, end);
    }

    /**
     * Right pop
     *
     * @param key
     * @param <T>
     * @return
     */
    public <T> T rPop(String key) {
        return (T) listOps.rightPop(key);
    }

    /**
     * Right push
     *
     * @param key
     * @param values
     * @return
     */
    public Long rPush(String key, Object... values) {
        return listOps.rightPush(key, values);
    }

    /**
     * Right push
     *
     * @param srcKey
     * @param dstKey
     * @param <T>
     * @return
     */
    public <T> T rPopLPush(String srcKey, String dstKey) {
        return (T) listOps.rightPopAndLeftPush(srcKey, dstKey);
    }

    /**
     * key Set
     *
     * @param key
     * @param members
     * @return
     */
    public Long sAdd(String key, Object... members) {
        return setOps.add(key, members);
    }


    /**
     * Remove Return pop
     *
     * @param key
     * @param <T>
     * @return
     */
    public <T> T sPop(String key) {
        return (T) setOps.pop(key);
    }

    /**
     * Return key to Keys value
     * <p>
     * if null return
     *
     * @param key
     * @return
     */
    public Set sMembers(String key) {
        return setOps.members(key);
    }

    /**
     * Check key Contains to member
     *
     * @param key
     * @param member
     * @return
     */
    public boolean sIsMember(String key, Object member) {
        return setOps.isMember(key, member);
    }

    /**
     * Return key and Other keys
     *
     * @param key
     * @param otherKey
     * @return
     */
    public Set sInter(String key, String otherKey) {
        return setOps.intersect(key, otherKey);
    }

    /**
     * Return key and Other keys
     *
     * @param key
     * @param otherKeys
     * @return
     */
    public Set sInter(String key, Collection<String> otherKeys) {
        return setOps.intersect(key, otherKeys);
    }

    /**
     * Return List random key
     *
     * @param key
     * @param <T>
     * @return
     */
    public <T> T sRandMember(String key) {
        return (T) setOps.randomMember(key);
    }

    /**
     * Return key's count
     *
     * @param key
     * @param count
     * @return
     */
    public List sRandMember(String key, int count) {
        return setOps.randomMembers(key, count);
    }

    /**
     * Remove keys or members
     *
     * @param key
     * @param members
     * @return
     */
    public Long sRem(String key, Object... members) {
        return setOps.remove(key, members);
    }

    /**
     * Return keys and otherKey
     *
     * @param key
     * @param otherKey
     * @return
     */
    public Set sUnion(String key, String otherKey) {
        return setOps.union(key, otherKey);
    }

    /**
     * Return keys and otherKey
     *
     * @param key
     * @param otherKeys
     * @return
     */
    public Set sUnion(String key, Collection<String> otherKeys) {
        return setOps.union(key, otherKeys);
    }

    /**
     * Return keys and otherKey - DIFF
     *
     * @param key
     * @param otherKey
     * @return
     */
    public Set sDiff(String key, String otherKey) {
        return setOps.difference(key, otherKey);
    }

    /**
     * Return keys and otherKey - DIFF
     *
     * @param key
     * @param otherKeys
     * @return
     */
    public Set sDiff(String key, Collection<String> otherKeys) {
        return setOps.difference(key, otherKeys);
    }

    /**
     * Return key score
     *
     * @param key
     * @param member
     * @return
     */
    public Double zScore(String key, Object member) {
        return zSetOps.score(key, member);
    }

    public Boolean zAdd(String key, Object member, double score) {
        return zSetOps.add(key, member, score);
    }

    public Long zAdd(String key, Map<Object, Double> scoreMembers) {
        Set<ZSetOperations.TypedTuple<Object>> tuples = new HashSet<>();
        scoreMembers.forEach((k, v) -> {
            tuples.add(new DefaultTypedTuple<>(k, v));
        });
        return zSetOps.add(key, tuples);
    }

    public Long zCard(String key) {
        return zSetOps.zCard(key);
    }

    public Long zCount(String key, double min, double max) {
        return zSetOps.count(key, min, max);
    }

    public Double zIncrBy(String key, Object member, double score) {
        return zSetOps.incrementScore(key, member, score);
    }

    public Set zRange(String key, long start, long end) {
        return zSetOps.range(key, start, end);
    }

    public Set zRevrange(String key, long start, long end) {
        return zSetOps.reverseRange(key, start, end);
    }

    public Set zRangeByScore(String key, double min, double max) {
        return zSetOps.rangeByScore(key, min, max);
    }

    public Long zRank(String key, Object member) {
        return zSetOps.rank(key, member);
    }

    public Long zRevrank(String key, Object member) {
        return zSetOps.reverseRank(key, member);
    }

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
