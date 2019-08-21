package org.yugh.gatewaynew.cache;

import javax.annotation.Nullable;
import java.time.Duration;

/**
 * cache key
 *
 * @author yugenhai
 */
public interface ICacheKey {

    /**
     * Get prefix
     *
     * @return
     */
    String getPrefix();

    /**
     * Timeout
     *
     * @return
     */
    @Nullable
    default Duration getExpire() {
        return null;
    }


}
