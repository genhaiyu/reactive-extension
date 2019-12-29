package org.yugh.coral.core.utils;


import org.yugh.coral.core.config.distribute.SimpleDistributeConfig;

/**
 * @author yugenhai
 */
public class SnowFlakeGenerateUtils {


    /**
     * see ${@link SimpleDistributeConfig}
     *
     * @return
     */
    public static Long snowFlakeGenerate() {
        SimpleDistributeConfig simpleDistributeConfig = new SimpleDistributeConfig();
        if (simpleDistributeConfig.getWorkId() > 0 && simpleDistributeConfig.getDataCenterId() > 0) {
            return new SnowFlakeGenerated(simpleDistributeConfig.getWorkId(), simpleDistributeConfig.getDataCenterId()).nextId();
        }
        return new SnowFlakeGenerated(0, 0).nextId();
    }
}
