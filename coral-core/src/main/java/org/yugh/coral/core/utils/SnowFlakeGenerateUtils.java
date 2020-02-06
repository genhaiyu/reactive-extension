package org.yugh.coral.core.utils;


import org.springframework.util.Assert;
import org.yugh.coral.core.config.distribute.SimpleDistributeConfig;
import org.yugh.coral.core.config.handler.ApplicationContextHelper;

/**
 * @author yugenhai
 */
public class SnowFlakeGenerateUtils {

    private static final String SIMPLE_DISTRIBUTE_CONFIG_NAME = "simpleDistributeConfig";


    /**
     * see ${@link SimpleDistributeConfig}
     *
     * @return Long
     */
    public static Long snowFlakeGenerate() {
        SimpleDistributeConfig simpleDistributeConfig = ApplicationContextHelper.getBean(SIMPLE_DISTRIBUTE_CONFIG_NAME);
        Assert.notNull(simpleDistributeConfig, () -> "simpleDistributeConfig '" + simpleDistributeConfig + "' failed to load");
        if (simpleDistributeConfig.getWorkId() > 0 && simpleDistributeConfig.getDataCenterId() > 0) {
            return new SnowFlakeGenerated(simpleDistributeConfig.getWorkId(), simpleDistributeConfig.getDataCenterId()).nextId();
        }
        return new SnowFlakeGenerated(0, 0).nextId();
    }
}
