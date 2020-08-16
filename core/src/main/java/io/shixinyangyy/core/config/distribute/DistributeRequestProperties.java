/*
 *
 * Copyright (c) 2014-2020, yugenhai108@gmail.com.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 */

package io.shixinyangyy.core.config.distribute;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * An ID generation strategy.
 *
 * @author Shixinyangyy
 */
@ConfigurationProperties(prefix = "shixinyangyy.distribute")
public class DistributeRequestProperties {

    /**
     * dataCenterId (0~31)
     *
     * {@link SnowFlake#SnowFlake(long datacenterId, long machineId)}
     */
    private int dataCenterId;

    /**
     * machineId (0~31)
     *
     * {@link SnowFlake#SnowFlake(long datacenterId, long machineId)}
     */
    private int machineId;

    public int getDataCenterId() {
        return dataCenterId;
    }

    public void setDataCenterId(int dataCenterId) {
        this.dataCenterId = dataCenterId;
    }

    public int getMachineId() {
        return machineId;
    }

    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
