/*
 * Copyright (c) 2019-2029, yugenhai108@gmail.com.
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

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.embedded.jetty.JettyServerCustomizer;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.reactive.server.ReactiveWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Role;
import org.springframework.http.client.reactive.*;
import org.yugh.coral.boot.rest.CustomRestTemplateCustomizer;
import org.yugh.coral.core.common.constant.StringPool;
import reactor.netty.http.server.HttpServer;

import java.util.function.Function;

/**
 * @author yugenhai
 */
@Configuration
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class BootContextConfig {


    /**
     * default Max Con Thread 200
     */
    @Value("${server.jetty.max-thread:200}")
    private int maxThreads;

    /**
     * default Min Con Thread 8
     */
    @Value("${server.jetty.min-thread:20}")
    private int minThreads;


    @Bean
    @Qualifier("customRestTemplateCustomizer")
    public CustomRestTemplateCustomizer customRestTemplateCustomizer() {
        return new CustomRestTemplateCustomizer();
    }

    @Bean
    @DependsOn(value = {"customRestTemplateCustomizer"})
    public RestTemplateBuilder restTemplateBuilder() {
        return new RestTemplateBuilder(customRestTemplateCustomizer());
    }


    @Configuration
    @ConditionalOnMissingBean(ClientHttpConnector.class)
    @ConditionalOnClass(org.eclipse.jetty.reactive.client.ReactiveRequest.class)
    @ConditionalOnProperty(value = StringPool.SELECT_CONTAINER_TYPE, havingValue = "true", matchIfMissing = true)
    public static class SelectContainerJettyAutoConfiguration {

        @Bean
        @ConditionalOnMissingBean
        public JettyResourceFactory jettyClientResourceFactory() {
            return new JettyResourceFactory();
        }

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


    @Configuration
    @ConditionalOnMissingBean(ClientHttpConnector.class)
    @ConditionalOnClass(reactor.netty.http.client.HttpClient.class)
    @ConditionalOnProperty(value = StringPool.SELECT_CONTAINER_TYPE, havingValue = "false", matchIfMissing = true)
    public static class SelectContainerReactorAutoConfiguration {

        @Bean
        @ConditionalOnMissingBean
        public ReactorResourceFactory reactorClientResourceFactory() {
            return new ReactorResourceFactory();
        }

        @Bean
        public ReactorClientHttpConnector reactorClientHttpConnector(ReactorResourceFactory reactorResourceFactory) {
            return new ReactorClientHttpConnector(reactorResourceFactory, Function.identity());
        }
    }


    @Bean
    public JettyServletWebServerFactory jettyServletWebServerFactory(JettyServerCustomizer jettyServerCustomizer) {
        JettyServletWebServerFactory jettyServletWebServerFactory = new JettyServletWebServerFactory();
        jettyServletWebServerFactory.addServerCustomizers(jettyServerCustomizer);
        return jettyServletWebServerFactory;
    }

    @Bean
    public JettyServerCustomizer jettyServerCustomizer() {
        return this::threadPool;
    }

    private void threadPool(Server server) {
        // Tweak the connection config used by Jetty to handle incoming HTTP
        final QueuedThreadPool threadPool = server.getBean(QueuedThreadPool.class);
        // Jetty default Max Con Thread 200
        threadPool.setMaxThreads(maxThreads);
        // Jetty default Min Con Thread 8
        threadPool.setMinThreads(minThreads);
        // Jetty default Max IdleTimeout 60000ms
        threadPool.setIdleTimeout(60000);
    }


    @Configuration
    @ConditionalOnMissingBean(ReactiveWebServerFactory.class)
    @ConditionalOnClass({ HttpServer.class })
    @ConditionalOnProperty(value = StringPool.SELECT_CONTAINER_TYPE, havingValue = "false", matchIfMissing = true)
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


    /*1**************************************** Business **********************************************************/

    /**
     * Check Custom Setting Properties
     *
     * @return
     */
//    @Bean
//    @ConditionalOnProperty("proxy.enable")
//    @ConfigurationProperties("proxy")
//    public ProxyProperties proxyProperties() {
//        return new ProxyProperties();
//    }
    /*2**************************************** Business **********************************************************/
}
