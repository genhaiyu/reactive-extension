package org.yugh.coral.core.config.distribute;


import org.springframework.util.Assert;
import org.yugh.coral.core.config.handler.ApplicationContextHelper;

/**
 * @author yugenhai
 */
public class SimpleSnowFlakeGenerated {

    private static final String SIMPLE_DISTRIBUTE_CONFIG_NAME = "simpleDistributeConfig";

    /**
     * @return Long
     */
    public static Long snowFlakeGenerate() {
        SimpleDistributeConfig simpleDistributeConfig = ApplicationContextHelper.getBean(SIMPLE_DISTRIBUTE_CONFIG_NAME);
        Assert.notNull(simpleDistributeConfig, () -> "simpleDistributeConfig '" + simpleDistributeConfig + "' failed to load");
        if (simpleDistributeConfig.getDatacenterId() > 0x00 && simpleDistributeConfig.getMachineId() > 0x00) {
            return new SnowFlakeGenerated(simpleDistributeConfig.getDatacenterId(), simpleDistributeConfig.getMachineId()).nextId();
        }
        return new SnowFlakeGenerated(0x00, 0x00).nextId();
    }
}
