package org.yugh.coral.auth.cache;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.lang.Nullable;

import java.time.Duration;

/**
 * cache key
 *
 * @author yugenhai
 */
@Getter
@ToString
@AllArgsConstructor
public class CacheKey {


    /**
     * redis key
     */
    private String key;

    /**
     * Timeout -> second
     */
    @Nullable
    private Duration expire;

    /**
     * Key Constructor
     *
     * @param key
     */
    protected CacheKey(String key) {
        this.key = key;
    }

}
