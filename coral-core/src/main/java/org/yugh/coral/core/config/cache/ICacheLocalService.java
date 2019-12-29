package org.yugh.coral.core.config.cache;

/**
 * @author yugenhai
 */
public interface ICacheLocalService {

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
