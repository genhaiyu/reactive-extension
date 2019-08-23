package org.yugh.gateway.cache;

import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.yugh.auth.util.StringPool;

import javax.annotation.Nullable;
import java.time.Duration;

/**
 * cache key
 *
 * @author yugenhai
 */
public interface ICacheKey {

    /**
     * get Prefix
     *
     * @return
     */
    String getPrefix();

    /**
     * Time out
     *
     * @return
     */
    @Nullable
    default Duration getExpire() {
        return null;
    }

    /**
     * Load Cache key
     *
     * @param suffix 参数
     * @return cache key
     */
    default CacheKey getKey(Object... suffix) {
        String prefix = this.getPrefix();
        String key;
        if (ObjectUtils.isEmpty(suffix)) {
            key = prefix;
        } else {
            key = prefix.concat(StringUtils.arrayToDelimitedString(suffix, StringPool.COLON));
        }
        Duration expire = this.getExpire();
        return expire == null ? new CacheKey(key) : new CacheKey(key, expire);
    }

}
