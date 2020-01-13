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
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Role;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.JettyClientHttpConnector;
import org.springframework.http.client.reactive.JettyResourceFactory;
import org.yugh.coral.boot.rest.CustomRestTemplateCustomizer;
import org.yugh.coral.core.common.constant.StringPool;

/**
 * @author yugenhai
 */
@Configuration
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class BootContextConfig {

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


    /**
     * 在压测下 Jetty 性能最好最稳定, 在微服务中声明如下
     * # enable select Jetty
     * xx:
     *   pickup:
     *     container:
     *       enable: true
     * 自定义加载 jetty 和 netty
     */
    @Configuration
    @ConditionalOnMissingBean(ClientHttpConnector.class)
    @ConditionalOnClass(org.eclipse.jetty.reactive.client.ReactiveRequest.class)
    @ConditionalOnProperty(value = StringPool.PICKUP_CONTAINER_TYPE, havingValue = "true", matchIfMissing = true)
    protected static class PickupContainerAutoConfiguration {

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
}
