package org.yugh.auth.cache;

/**
 * 实例本身二级Guava缓存
 *
 * @author yugenhai
 */
public interface ICacheCommonService {

    /**
     * 加载缓存
     *
     * @param id
     * @return
     */
    Object loadCache(String id);

    /**
     * 获取缓存
     *
     * @param key
     * @return
     */
    Object getObjectByCache(String key);

    /**
     * 丢弃key
     *
     * @param key
     */
    void removeCacheKey(String key);
}
