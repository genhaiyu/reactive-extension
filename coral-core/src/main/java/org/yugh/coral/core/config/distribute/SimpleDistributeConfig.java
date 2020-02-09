package org.yugh.coral.core.config.distribute;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author yugenhai
 */
@Data
public class SimpleDistributeConfig {

    /**
     * 工作 ID (0~31)
     */
    @Value("${distribute.sequences.datacenter.datacenter-id:0}")
    private int datacenterId;

    /**
     * 数据中心 ID (0~31)
     */
    @Value("${distribute.sequences.machine.machine-id:0}")
    private int machineId;
}
