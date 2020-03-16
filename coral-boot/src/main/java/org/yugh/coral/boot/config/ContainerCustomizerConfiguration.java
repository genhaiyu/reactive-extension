/*
 * Copyright (c) 2014-2020, yugenhai108@gmail.com.
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.gnu.org/licenses/lgpl.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.yugh.coral.boot.config;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.server.handler.StatisticsHandler;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.reactive.server.ReactiveWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.JettyClientHttpConnector;
import org.springframework.http.client.reactive.JettyResourceFactory;
import org.springframework.http.client.reactive.ReactorResourceFactory;
import org.yugh.coral.boot.config.jetty.GracefulShutdownJettyServer;
import org.yugh.coral.core.config.ClientMessageInfo;
import reactor.netty.http.server.HttpServer;

/**
 * @author yugenhai
 */
@Slf4j
@Configuration
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class ContainerCustomizerConfiguration {

    @Configuration
    @ConditionalOnMissingBean(ClientHttpConnector.class)
    @ConditionalOnClass(org.eclipse.jetty.reactive.client.ReactiveRequest.class)
    @ConditionalOnProperty(value = ClientMessageInfo.JETTY_CONTAINER_CONFIG, havingValue = "true", matchIfMissing = true)
    public static class SelectContainerJettyAutoConfiguration {

        // private HttpClient httpClient;

        @Bean
        @ConditionalOnMissingBean
        public JettyResourceFactory jettyClientResourceFactory() {
            return new JettyResourceFactory();
        }

        /**
         * WebClient 配置
         * 同时支持  Jetty Reactive Streams HttpClient.
         *
         * @param jettyResourceFactory
         * @return
         */
        @Bean
        public JettyClientHttpConnector jettyClientHttpConnector(JettyResourceFactory jettyResourceFactory
        ) {
            SslContextFactory sslContextFactory = new SslContextFactory.Client();
            HttpClient httpClient = new HttpClient(sslContextFactory);
            httpClient.setExecutor(jettyResourceFactory.getExecutor());
            httpClient.setByteBufferPool(jettyResourceFactory.getByteBufferPool());
            httpClient.setScheduler(jettyResourceFactory.getScheduler());
            return new JettyClientHttpConnector(httpClient);
        }

//        @Bean
//        public WebClient webClient() {
//
//            HttpClient httpClient = new HttpClient();
//            // Further customizations...
//
//            ClientHttpConnector connector =
//                    new JettyClientHttpConnector(jettyClientResourceFactory(), httpClient);
//
//            return WebClient.builder().clientConnector(connector).build();
//        }
    }

    /**
     * Jetty 容器必须加载, 不区分 reactor-http , jetty-http
     * <p>
     * Jetty shutdown wait time 30000ms
     * Jetty default Max IdleTimeout 60000ms
     * Jetty default Min Con Thread 8
     * Jetty default Max Con Thread 200
     *
     * @param maxThreads
     * @param minThreads
     * @param idleTimeout
     * @param shutdownWaitTime
     * @return
     */
    @Bean
    public JettyServletWebServerFactory jettyServletWebServerFactory(
            @Value("${coral.jetty.initial-value.max-thread:200}") int maxThreads,
            @Value("${coral.jetty.initial-value.min-thread:20}") int minThreads,
            @Value("${coral.jetty.initial-value.idle-timeout:60000}") int idleTimeout,
            @Value("${coral.jetty.initial-value.shutdown-wait-time:30000}") int shutdownWaitTime

    ) {
        JettyServletWebServerFactory jettyServletWebServerFactory = new JettyServletWebServerFactory();
        jettyServletWebServerFactory.addServerCustomizers(server -> {
            // tweak the connection config used by jetty to handle incoming http
            QueuedThreadPool threadPool = server.getBean(QueuedThreadPool.class);
            threadPool.setMaxThreads(maxThreads);
            threadPool.setMinThreads(minThreads);
            threadPool.setIdleTimeout(idleTimeout);
            GracefulShutdownJettyServer.setServer(server);
            if (shutdownWaitTime > 0) {
                StatisticsHandler handler = new StatisticsHandler();
                handler.setHandler(server.getHandler());
                server.setHandler(handler);
                log.info("Shutdown wait time: " + shutdownWaitTime + " ms");
                server.setStopTimeout(shutdownWaitTime);
                // We will stop it through SimpleShutdownServer class.
                server.setStopAtShutdown(false);
            }
        });
        return jettyServletWebServerFactory;
    }


    @Configuration
    @ConditionalOnMissingBean(ReactiveWebServerFactory.class)
    @ConditionalOnClass({HttpServer.class})
    @ConditionalOnProperty(value = ClientMessageInfo.JETTY_CONTAINER_CONFIG, havingValue = "false", matchIfMissing = true)
    public static class SelectContainerNettyAutoConfiguration {

        @Bean
        @ConditionalOnMissingBean
        public ReactorResourceFactory reactorServerResourceFactory() {
            return new ReactorResourceFactory();
        }

        @Bean
        public NettyReactiveWebServerFactory nettyReactiveWebServerFactory(ReactorResourceFactory resourceFactory) {
            NettyReactiveWebServerFactory serverFactory = new NettyReactiveWebServerFactory();
            serverFactory.setResourceFactory(resourceFactory);
            return serverFactory;
        }
    }

}
