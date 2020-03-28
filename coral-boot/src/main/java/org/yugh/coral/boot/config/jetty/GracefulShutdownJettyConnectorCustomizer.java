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

import org.eclipse.jetty.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.embedded.jetty.JettyServerCustomizer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Shutdown Jetty Connector
 *
 * @author yugenhai
 */
public class GracefulShutdownJettyConnectorCustomizer implements JettyServerCustomizer {

    private static final Logger LOG = LoggerFactory.getLogger(GracefulShutdownJettyConnectorCustomizer.class);

    private static final int CHECK_INTERVAL = 10;
    private final ApplicationContext applicationContext;
    private final GracefulShutdownJettyProperties gracefulShutdownJettyProperties;
    private Server server;

    // private final Graceful;

    public GracefulShutdownJettyConnectorCustomizer(ApplicationContext ctx, GracefulShutdownJettyProperties props) {
        this.applicationContext = ctx;
        this.gracefulShutdownJettyProperties = props;
    }

    @Override
    public void customize(Server server) {
        this.server = server;
    }

    @EventListener(ContextClosedEvent.class)
    @Order(Ordered.HIGHEST_PRECEDENCE + 1)
    public void contextClosed(ContextClosedEvent event) throws InterruptedException {
//        if (server == null) {
//            return;
//        }

        if (isEventFromLocalContext(event)) {
            stopAcceptingNewRequests();
            shutdownThreadPoolExecutor();
        }
    }

    private void stopAcceptingNewRequests() {
       // graceful.shutdown();

       try
       {
           server.stop();
       }catch (Exception e){
           //
           LOG.warn("Jetty server stop error ", e);
       }

        LOG.info("Paused {} to stop accepting new requests", server);
    }

    private void shutdownThreadPoolExecutor() throws InterruptedException {
        ThreadPoolExecutor executor = getThreadPoolExecutor();

        if (executor != null) {
            executor.shutdown();
            awaitTermination(executor);
        }
    }

    private void awaitTermination(ThreadPoolExecutor executor) throws InterruptedException {
        for (long remaining = gracefulShutdownJettyProperties.getTimeout().getSeconds(); remaining > 0; remaining -= CHECK_INTERVAL) {
            if (executor.awaitTermination(CHECK_INTERVAL, TimeUnit.SECONDS)) {
                return;
            }

            LOG.info("{} thread(s) active, {} seconds remaining", executor.getActiveCount(), remaining);
        }

        logMessageIfThereAreStillActiveThreads(executor);
    }

    private void logMessageIfThereAreStillActiveThreads(ThreadPoolExecutor executor) {
        if (executor.getActiveCount() > 0) {
            LOG.warn("{} thread(s) still active, force shutdown", executor.getActiveCount());
        }
    }

    private ThreadPoolExecutor getThreadPoolExecutor() {
        Executor executor = server.getThreadPool();

        if (executor instanceof ThreadPoolExecutor) {
            return (ThreadPoolExecutor) executor;
        }

        return null;
    }

    private boolean isEventFromLocalContext(ContextClosedEvent event) {
        return event.getApplicationContext().equals(applicationContext);
    }

}
