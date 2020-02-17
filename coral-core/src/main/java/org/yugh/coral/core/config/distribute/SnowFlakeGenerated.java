package org.yugh.coral.core.config.distribute;


import org.springframework.util.Assert;
import org.yugh.coral.core.config.SimpleSnowFlakeGenerated;
import org.yugh.coral.core.config.handler.ApplicationContextHelper;

/**
 * @author yugenhai
 */
public class SnowFlakeGenerated implements SimpleSnowFlakeGenerated {

    private static final String SIMPLE_DISTRIBUTE_CONFIG_NAME = "simpleDistributeConfig";

    @Override
    public Long simpleSnowFlakeGenerated() {
        SimpleSnowFlakeGenerated generated = this::snowFlakeGenerated;
        return generated.simpleSnowFlakeGenerated();
    }


    /**
     * @return Long
     */
    private Long snowFlakeGenerated() {
        SimpleDistributeConfig simpleDistributeConfig = ApplicationContextHelper.getBean(SIMPLE_DISTRIBUTE_CONFIG_NAME);
        Assert.notNull(simpleDistributeConfig, () -> "simpleDistributeConfig '" + simpleDistributeConfig + "' failed to load");
        if (simpleDistributeConfig.getDatacenterId() > 0x00 && simpleDistributeConfig.getMachineId() > 0x00) {
            return new SnowFlake(simpleDistributeConfig.getDatacenterId(), simpleDistributeConfig.getMachineId()).nextId();
        }
        return new SnowFlake(0x00, 0x00).nextId();
    }
}
