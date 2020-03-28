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


package org.yugh.coral.boot.config;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.server.handler.StatisticsHandler;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.reactive.server.ReactiveWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.JettyClientHttpConnector;
import org.springframework.http.client.reactive.JettyResourceFactory;
import org.springframework.http.client.reactive.ReactorResourceFactory;
import org.yugh.coral.boot.config.jetty.GracefulShutdownJettyServer;
import org.yugh.coral.core.config.CoralJettyValueProperties;
import reactor.netty.http.server.HttpServer;

/**
 * Jetty, Netty Configuration.
 *
 * @author yugenhai
 */
@Deprecated
@Configuration(proxyBeanMethods = false)
public class ClientRequestAutoConfiguration {

    private static final Logger LOG = LoggerFactory.getLogger(ClientRequestAutoConfiguration.class);

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnMissingBean(ClientHttpConnector.class)
    @ConditionalOnClass(org.eclipse.jetty.reactive.client.ReactiveRequest.class)
    @ConditionalOnProperty(value = "coral.jetty.config", name = "enabled", havingValue = "true", matchIfMissing = true)
    public static class JettyAutoConfiguration {


        @Bean
        @ConditionalOnMissingBean
        public JettyResourceFactory jettyResourceFactory() {
            return new JettyResourceFactory();
        }

        /**
         * WebClient 配置
         * Jetty Reactive Streams HttpClient.
         *
         * @param jettyResourceFactory
         * @return
         */
        @Bean
        public JettyClientHttpConnector jettyClientHttpConnector(JettyResourceFactory jettyResourceFactory) {
            SslContextFactory sslContextFactory = new SslContextFactory.Client();
            HttpClient httpClient = new HttpClient(sslContextFactory);
            httpClient.setExecutor(jettyResourceFactory.getExecutor());
            httpClient.setByteBufferPool(jettyResourceFactory.getByteBufferPool());
            httpClient.setScheduler(jettyResourceFactory.getScheduler());
            return new JettyClientHttpConnector(httpClient);
        }
    }

    /**
     * Jetty shutdown wait time 30000ms
     * Jetty default Max IdleTimeout 60000ms
     * Jetty default Min Con Thread 8
     * Jetty default Max Con Thread 200
     *
     * @param coralJettyValueProperties
     * @return JettyServletWebServerFactory
     */
    @Bean
    public JettyServletWebServerFactory jettyServletWebServerFactory(CoralJettyValueProperties coralJettyValueProperties) {
        JettyServletWebServerFactory jettyServletWebServerFactory = new JettyServletWebServerFactory();
        jettyServletWebServerFactory.addServerCustomizers(server -> {
            // tweak the connection config used by jetty to handle incoming http
            QueuedThreadPool threadPool = server.getBean(QueuedThreadPool.class);
            threadPool.setMaxThreads(coralJettyValueProperties.getMaxThread());
            threadPool.setMinThreads(coralJettyValueProperties.getMinThread());
            threadPool.setIdleTimeout(coralJettyValueProperties.getIdleTimeout());
            GracefulShutdownJettyServer.setServer(server);
            if (coralJettyValueProperties.getShutdownWaitTime() > 0) {
                StatisticsHandler handler = new StatisticsHandler();
                handler.setHandler(server.getHandler());
                server.setHandler(handler);
                LOG.info("Shutdown wait time: " + coralJettyValueProperties.getShutdownWaitTime() + " ms");
                server.setStopTimeout(coralJettyValueProperties.getShutdownWaitTime());
                // We will stop it through SimpleShutdownServer class.
                server.setStopAtShutdown(false);
            }
        });
        return jettyServletWebServerFactory;
    }


    @Configuration(proxyBeanMethods = false)
    @ConditionalOnMissingBean(ReactiveWebServerFactory.class)
    @ConditionalOnClass({HttpServer.class})
    @ConditionalOnProperty(value = "coral.jetty.config", name = "enabled", havingValue = "false", matchIfMissing = true)
    public static class ReactorNettyAutoConfiguration {

        @Bean
        @ConditionalOnMissingBean
        public ReactorResourceFactory reactorResourceFactory() {
            return new ReactorResourceFactory();
        }

        @Bean
        public NettyReactiveWebServerFactory nettyReactiveWebServerFactory(ReactorResourceFactory reactorResourceFactory) {
            NettyReactiveWebServerFactory serverFactory = new NettyReactiveWebServerFactory();
            serverFactory.setResourceFactory(reactorResourceFactory);
            return serverFactory;
        }
    }
}
