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

package org.yugh.coral.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "coral.jetty.initial-value")
public class CoralJettyValueProperties {

    /**
     * Max Thread.
     */
    private int maxThread;
    /**
     * Min Thread.
     */
    private int minThread;
    /**
     * IdleTimeout
     */
    private Integer idleTimeout;
    /**
     * ShutdownWaitTime
     */
    private Integer shutdownWaitTime;

    public int getMaxThread() {
        return maxThread;
    }

    public void setMaxThread(int maxThread) {
        this.maxThread = maxThread;
    }

    public int getMinThread() {
        return minThread;
    }

    public void setMinThread(int minThread) {
        this.minThread = minThread;
    }

    public Integer getIdleTimeout() {
        return idleTimeout;
    }

    public void setIdleTimeout(Integer idleTimeout) {
        this.idleTimeout = idleTimeout;
    }

    public Integer getShutdownWaitTime() {
        return shutdownWaitTime;
    }

    public void setShutdownWaitTime(Integer shutdownWaitTime) {
        this.shutdownWaitTime = shutdownWaitTime;
    }
}
