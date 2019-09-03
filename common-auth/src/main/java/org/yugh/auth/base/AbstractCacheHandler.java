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
            .concurrencyLevel(10)
            .expireAfterWrite(30, TimeUnit.SECONDS)
            .initialCapacity(100)
            .maximumSize(1000)
            .build(new CacheLoader<String, Optional<Object>>() {
                @Override
                public Optional<Object> load(
                        String key) throws Exception {
                    return Optional.fromNullable(loadCache(key));
                }
            });
}
