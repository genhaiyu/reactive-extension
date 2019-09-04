package org.yugh.auth.base;

import com.google.common.base.Optional;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.yugh.auth.cache.ICacheCommonService;
import org.yugh.auth.cache.IRedisCache;

import java.util.concurrent.TimeUnit;

/**
 * 实例本身临时Guava缓存
 *
 * @author yugenhai
 */
public abstract class AbstractCacheHandler implements ICacheCommonService, IRedisCache {


    /**
     * 实例本身临时Guava缓存
     * <p>
     * {@link LoadingCache} and {@link CacheBuilder}
     *
     * @author yugenhai
     */
    public LoadingCache<String, Optional<Object>> cache = CacheBuilder
            .newBuilder()
            // 设置并发级别为10，并发级别是指可以同时写缓存的线程数
            .concurrencyLevel(10)
            // 给定时间内没有被读/写访问，则回收。
            // .refreshAfterWrite(600, TimeUnit.SECONDS)
            // 这个方法是根据某个键值对被创建或值被替换后多少时间移除
            .expireAfterWrite(300, TimeUnit.SECONDS)
            // 这个方法是根据某个键值对最后一次访问之后多少时间后移除
            //.expireAfterAccess(GuavaProperties.GUAVA_EXPIRETIME, TimeUnit.SECONDS)
            // 设置缓存容器的初始容量为100
            .initialCapacity(100)
            // 设置缓存最大容量为10000，超过10000之后就会按照LRU最近虽少使用算法来移除缓存项
            .maximumSize(10000)
            .build(new CacheLoader<String, Optional<Object>>() {
                @Override
                public Optional<Object> load(String key) {
                    return Optional.fromNullable(loadCache(key));
                }
            });
}
