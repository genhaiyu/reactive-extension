package org.yugh.coral.auth.cache;

import java.time.Duration;
import java.util.*;
import java.util.function.Supplier;

/**
 * @author yugenhai
 */
public interface IRedisCache {


    /**
     * Set Cache Key
     *
     * @param cacheKey
     * @param value
     */
    void set(CacheKey cacheKey, Object value);

    /**
     * Simple Set Key
     *
     * @param key
     * @param value
     */
    void set(String key, Object value);

    /**
     * Set key -> second
     * If key Exist = Override
     *
     * @param key
     * @param value
     * @param timeout
     */
    void setEx(String key, Object value, Duration timeout);

    /**
     * Set key -> second
     * If key Exist = Override
     *
     * @param key
     * @param value
     * @param seconds
     */
    void setEx(String key, Object value, Long seconds);

    /**
     * Get key
     * <p>
     * Or nil
     *
     * @param key
     * @param <T>
     * @return
     */
    <T> T get(String key);


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
    <T> T get(String key, Supplier<T> loader);

    /**
     * Get Cache key
     * <p>
     * Or nil
     *
     * @param cacheKey
     * @param <T>
     * @return
     */
    <T> T get(CacheKey cacheKey);


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
    <T> T get(CacheKey cacheKey, Supplier<T> loader);

    /**
     * Del key
     *
     * @param key
     * @return
     */
    Boolean del(String key);

    /**
     * Del Cache key
     *
     * @param key
     * @return
     */
    Boolean del(CacheKey key);


    /**
     * Del keys
     *
     * @param keys
     * @return
     */
    Long del(String... keys);

    /**
     * Del list key
     *
     * @param keys
     * @return
     */
    Long del(Collection<String> keys);

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
    Set<String> keys(String pattern);

    /**
     * Set keysValues
     *
     * @param keysValues
     */
    void mSet(Object... keysValues);

    /**
     * Return Keys
     * <p>
     * Or nil
     *
     * @param keys
     * @return
     */
    List<Object> mGet(String... keys);

    /**
     * Return Keys
     * <p>
     * Or nil
     *
     * @param keys
     * @return
     */
    List<Object> mGet(Collection<String> keys);

    /**
     * Decrement key
     *
     * @param key
     * @return
     */
    Long decr(String key);


    /**
     * Decrement key
     *
     * @param key
     * @param longValue
     * @return
     */
    Long decrBy(String key, long longValue);

    /**
     * Increment key
     *
     * @param key
     * @return
     */
    Long incr(String key);

    /**
     * Increment key
     *
     * @param key
     * @param longValue
     * @return
     */
    Long incrBy(String key, long longValue);

    /**
     * Get Count key
     *
     * @param key
     * @return
     */
    Long getCounter(String key);

    /**
     * Exists key
     *
     * @param key
     * @return
     */
    Boolean exists(String key);

    /**
     * Get random key
     *
     * @return
     */
    Object randomKey();

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
    void rename(String oldkey, String newkey);

    /**
     * Move key to Other db
     * <p>
     * If key NoExists it's Failed
     *
     * @param key
     * @param dbIndex
     * @return
     */
    Boolean move(String key, int dbIndex);

    /**
     * Set key expire
     *
     * @param key
     * @param seconds
     * @return
     */
    Boolean expire(String key, long seconds);

    /**
     * Set key expire
     *
     * @param key
     * @param timeout
     * @return
     */
    Boolean expire(String key, Duration timeout);

    /**
     * Set key expire To Date
     *
     * @param key
     * @param date
     * @return
     */
    Boolean expireAt(String key, Date date);

    /**
     * Set key to expire
     *
     * @param key
     * @param unixTime
     * @return
     */
    Boolean expireAt(String key, long unixTime);

    /**
     * Set key to expire By milliseconds
     *
     * @param key
     * @param milliseconds
     * @return
     */
    Boolean pexpire(String key, long milliseconds);

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
    <T> T getSet(String key, Object value);

    /**
     * Del Key's expire
     * <p>
     * Change Final
     *
     * @param key
     * @return
     */
    Boolean persist(String key);

    /**
     * Reurn key's Type
     *
     * @param key
     * @return
     */
    String type(String key);

    /**
     * Get key time to live
     *
     * @param key
     * @return
     */
    Long ttl(String key);

    /**
     * Get key time to live  - MILLISECONDS
     *
     * @param key
     * @return
     */
    Long pttl(String key);

    /**
     * Set key field to Value
     *
     * @param key
     * @param field
     * @param value
     */
    void hSet(String key, Object field, Object value);

    /**
     * Set Map key-value to Hash
     *
     * @param key
     * @param hash
     */
    void hMset(String key, Map<Object, Object> hash);

    /**
     * Get key to Field
     *
     * @param key
     * @param field
     * @param <T>
     * @return
     */
    <T> T hGet(String key, Object field);

    /**
     * Get key to Fields
     *
     * @param key
     * @param fields
     * @return
     */
    List hmGet(String key, Object... fields);

    /**
     * Get key to hashKeys
     *
     * @param key
     * @param hashKeys
     * @return
     */
    List hmGet(String key, Collection<Object> hashKeys);

    /**
     * Del key to fields
     *
     * @param key
     * @param fields
     * @return
     */
    Long hDel(String key, Object... fields);

    /**
     * Get key to field - Exists
     *
     * @param key
     * @param field
     * @return
     */
    Boolean hExists(String key, Object field);

    /**
     * Return Key - field name
     *
     * @param key
     * @return
     */
    Map hGetAll(String key);

    /**
     * Return key All Hash
     *
     * @param key
     * @return
     */
    List hVals(String key);

    /**
     * Get keys Hash , hfields
     * <p>
     * Set
     *
     * @param key
     * @return
     */
    Set<Object> hKeys(String key);

    /**
     * Get key Hash size
     *
     * @param key
     * @return
     */
    Long hLen(String key);

    /**
     * Increment key to field
     *
     * @param key
     * @param field
     * @param value
     * @return
     */
    Long hIncrBy(String key, Object field, long value);

    /**
     * Increment key to field - float
     *
     * @param key
     * @param field
     * @param value
     * @return
     */
    Double hIncrByFloat(String key, Object field, double value);

    /**
     * Return key to Index
     *
     * @param key
     * @param index
     * @param <T>
     * @return
     */
    <T> T lIndex(String key, long index);

    /**
     * Return key length
     * <p>
     * or 0
     *
     * @param key
     * @return
     */
    Long lLen(String key);

    /**
     * Remove key left
     *
     * @param key
     * @param <T>
     * @return
     */
    <T> T lPop(String key);

    /**
     * lPush
     *
     * @param key
     * @param values
     * @return
     */
    Long lPush(String key, Object... values);

    /**
     * Set key to Index
     *
     * @param key
     * @param index
     * @param value
     */
    void lSet(String key, long index, Object value);

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
    Long lRem(String key, long count, Object value);


    /**
     * Key range
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    List lRange(String key, long start, long end);

    /**
     * Trim key
     *
     * @param key
     * @param start
     * @param end
     */
    void lTrim(String key, long start, long end);

    /**
     * Right pop
     *
     * @param key
     * @param <T>
     * @return
     */
    <T> T rPop(String key);

    /**
     * Right push
     *
     * @param key
     * @param values
     * @return
     */
    Long rPush(String key, Object... values);

    /**
     * Right push
     *
     * @param srcKey
     * @param dstKey
     * @param <T>
     * @return
     */
    <T> T rPopLPush(String srcKey, String dstKey);

    /**
     * key Set
     *
     * @param key
     * @param members
     * @return
     */
    Long sAdd(String key, Object... members);


    /**
     * Remove Return pop
     *
     * @param key
     * @param <T>
     * @return
     */
    <T> T sPop(String key);

    /**
     * Return key to Keys value
     * <p>
     * if null return
     *
     * @param key
     * @return
     */
    Set sMembers(String key);

    /**
     * Check key Contains to member
     *
     * @param key
     * @param member
     * @return
     */
    boolean sIsMember(String key, Object member);

    /**
     * Return key and Other keys
     *
     * @param key
     * @param otherKey
     * @return
     */
    Set sInter(String key, String otherKey);

    /**
     * Return key and Other keys
     *
     * @param key
     * @param otherKeys
     * @return
     */
    Set sInter(String key, Collection<String> otherKeys);

    /**
     * Return List random key
     *
     * @param key
     * @param <T>
     * @return
     */
    <T> T sRandMember(String key);

    /**
     * Return key's count
     *
     * @param key
     * @param count
     * @return
     */
    List sRandMember(String key, int count);

    /**
     * Remove keys or members
     *
     * @param key
     * @param members
     * @return
     */
    Long sRem(String key, Object... members);

    /**
     * Return keys and otherKey
     *
     * @param key
     * @param otherKey
     * @return
     */
    Set sUnion(String key, String otherKey);

    /**
     * Return keys and otherKey
     *
     * @param key
     * @param otherKeys
     * @return
     */
    Set sUnion(String key, Collection<String> otherKeys);

    /**
     * Return keys and otherKey - DIFF
     *
     * @param key
     * @param otherKey
     * @return
     */
    Set sDiff(String key, String otherKey);

    /**
     * Return keys and otherKey - DIFF
     *
     * @param key
     * @param otherKeys
     * @return
     */
    Set sDiff(String key, Collection<String> otherKeys);

    /**
     * 返回有序集 key 中，成员 member 的 score 值。
     * 如果 member 元素不是有序集 key 的成员，或 key 不存在，返回 nil 。
     *
     * @param key
     * @param member
     * @return
     */
    Double zScore(String key, Object member);

    /**
     * 将一个或多个 member 元素及其 score 值加入到有序集 key 当中。
     * 如果某个 member 已经是有序集的成员，那么更新这个 member 的 score 值，
     * 并通过重新插入这个 member 元素，来保证该 member 在正确的位置上。
     *
     * @param key
     * @param member
     * @param score
     * @return
     */
    Boolean zAdd(String key, Object member, double score);

    /**
     * 将一个或多个 member 元素及其 score 值加入到有序集 key 当中。
     * 如果某个 member 已经是有序集的成员，那么更新这个 member 的 score 值，
     * 并通过重新插入这个 member 元素，来保证该 member 在正确的位置上。
     *
     * @param key
     * @param scoreMembers
     * @return
     */
    Long zAdd(String key, Map<Object, Double> scoreMembers);

    /**
     * 返回有序集 key 的基数。
     *
     * @param key
     * @return
     */
    Long zCard(String key);

    /**
     * 返回有序集 key 中， score 值在 min 和 max 之间(默认包括 score 值等于 min 或 max )的成员的数量。
     * 关于参数 min 和 max 的详细使用方法，请参考 ZRANGEBYSCORE 命令。
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    Long zCount(String key, double min, double max);

    /**
     * 为有序集 key 的成员 member 的 score 值加上增量 increment
     *
     * @param key
     * @param member
     * @param score
     * @return
     */
    Double zIncrBy(String key, Object member, double score);

    /**
     * 返回有序集 key 中，指定区间内的成员。
     * 其中成员的位置按 score 值递增(从小到大)来排序。
     * 具有相同 score 值的成员按字典序(lexicographical order )来排列。
     * 如果你需要成员按 score 值递减(从大到小)来排列，请使用 ZREVRANGE 命令。
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    Set zRange(String key, long start, long end);

    /**
     * 返回有序集 key 中，指定区间内的成员。
     * 其中成员的位置按 score 值递减(从大到小)来排列。
     * 具有相同 score 值的成员按字典序的逆序(reverse lexicographical order)排列。
     * 除了成员按 score 值递减的次序排列这一点外， ZREVRANGE 命令的其他方面和 ZRANGE 命令一样。
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    Set zRevrange(String key, long start, long end);

    /**
     * 返回有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员。
     * 有序集成员按 score 值递增(从小到大)次序排列。
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    Set zRangeByScore(String key, double min, double max);

    /**
     * 返回有序集 key 中成员 member 的排名。其中有序集成员按 score 值递增(从小到大)顺序排列。
     * 排名以 0 为底，也就是说， score 值最小的成员排名为 0 。
     * 使用 ZREVRANK 命令可以获得成员按 score 值递减(从大到小)排列的排名。
     *
     * @param key
     * @param member
     * @return
     */
    Long zRank(String key, Object member);

    /**
     * 返回有序集 key 中成员 member 的排名。其中有序集成员按 score 值递减(从大到小)排序。
     * 排名以 0 为底，也就是说， score 值最大的成员排名为 0 。
     * 使用 ZRANK 命令可以获得成员按 score 值递增(从小到大)排列的排名。
     *
     * @param key
     * @param member
     * @return
     */
    Long zRevrank(String key, Object member);


    /**
     * 移除有序集 key 中的一个或多个成员，不存在的成员将被忽略。
     * 当 key 存在但不是有序集类型时，返回一个错误。
     *
     * @param key
     * @param members
     * @return
     */
    Long zRem(String key, Object... members);
}
