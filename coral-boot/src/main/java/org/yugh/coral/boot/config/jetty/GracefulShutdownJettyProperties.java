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


package org.yugh.coral.boot.config.jetty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.StringJoiner;

/**
 * Shutdown Jetty Properties
 *
 * @author yugenhai
 */
@ConfigurationProperties("coral.jetty.shutdown")
public class GracefulShutdownJettyProperties implements InitializingBean {

    private static final Logger LOG = LoggerFactory.getLogger(GracefulShutdownJettyProperties.class);

    /**
     * Indicates whether graceful shutdown is enabled or not.
     */
    private boolean enabled;

    /**
     * The number of seconds to wait for active threads to finish before shutting down the embedded web container.
     */
    private Duration timeout = Duration.ofSeconds(60);

    /**
     * The number of seconds to wait before starting the graceful shutdown. During this time, the health checker returns
     * OUT_OF_SERVICE.
     */
    private Duration wait = Duration.ofSeconds(10);

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Duration getTimeout() {
        return timeout;
    }

    public void setTimeout(Duration timeout) {
        this.timeout = timeout;
    }

    public Duration getWait() {
        return wait;
    }

    public void setWait(Duration wait) {
        this.wait = wait;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", "GracefulShutdownJettyProperties[", "]")
                .add("enabled=" + isEnabled())
                .add("timeout=" + getTimeout())
                .add("wait=" + getWait())
                .toString();
    }

    @Override
    public void afterPropertiesSet() {
        LOG.info(toString());
    }
}

