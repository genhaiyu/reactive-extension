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
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * Shutdown healthIndicator.
 *
 * @author yugenhai
 */
public class GracefulShutdownJettyHealthIndicator implements HealthIndicator {

    private static final Logger LOG = LoggerFactory.getLogger(GracefulShutdownJettyHealthIndicator.class);
    private final ApplicationContext applicationContext;
    private final GracefulShutdownJettyProperties gracefulShutdownJettyProperties;
    private Health health = Health.up().build();

    public GracefulShutdownJettyHealthIndicator(ApplicationContext ctx, GracefulShutdownJettyProperties properties) {
        this.applicationContext = ctx;
        this.gracefulShutdownJettyProperties = properties;
    }

    @Override
    public Health health() {
        return health;
    }

    @EventListener(ContextClosedEvent.class)
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public void contextClosed(ContextClosedEvent event) throws InterruptedException {
        if (isEventFromLocalContext(event)) {
            updateHealthToOutOfService();
            waitForKubernetesToSeeOutOfService();
        }
    }

    private void updateHealthToOutOfService() {
        // update out of service
        health = Health.outOfService().build();
        LOG.info("Health status set to out of service");
    }

    private void waitForKubernetesToSeeOutOfService() throws InterruptedException {
        // Wait for Kubernetes
        LOG.info("Wait {} seconds for Kubernetes to see the out of service status", gracefulShutdownJettyProperties.getWait().getSeconds());
        Thread.sleep(gracefulShutdownJettyProperties.getWait().toMillis());
    }

    private boolean isEventFromLocalContext(ContextClosedEvent event) {
        return event.getApplicationContext().equals(applicationContext);
    }
}
