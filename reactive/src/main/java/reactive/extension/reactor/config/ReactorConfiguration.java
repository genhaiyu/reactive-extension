package reactive.extension.reactor.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactive.extension.basement.config.DispatcherRequestCustomizer;
import reactive.extension.basement.request.RequestMessageDefinition;
import reactive.extension.reactor.config.jetty.GracefulShutdownJettyConnectorCustomizer;
import reactive.extension.reactor.config.jetty.GracefulShutdownJettyContainerCustomizer;
import reactive.extension.reactor.config.jetty.GracefulShutdownJettyHealthIndicator;
import reactive.extension.reactor.config.jetty.GracefulShutdownJettyProperties;
import reactive.extension.reactor.reactive.ReactiveWebContextFilter;
import reactive.extension.reactor.reactive.ReactiveWebHeaderProcessor;
import reactive.extension.reactor.servlet.ServletWebContextListener;

/**
 * @author Genhai Yu
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({GracefulShutdownJettyProperties.class})
public class ReactorConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public GracefulShutdownJettyHealthIndicator gracefulShutdownJettyHealthIndicator(ApplicationContext ctx, GracefulShutdownJettyProperties properties) {
        return new GracefulShutdownJettyHealthIndicator(ctx, properties);
    }

    @Bean
    @ConditionalOnMissingBean
    public GracefulShutdownJettyContainerCustomizer gracefulShutdownJettyContainerCustomizer(GracefulShutdownJettyConnectorCustomizer gracefulShutdownJettyConnectorCustomizer) {
        return new GracefulShutdownJettyContainerCustomizer(gracefulShutdownJettyConnectorCustomizer);
    }

    @Bean
    @ConditionalOnMissingBean
    public GracefulShutdownJettyConnectorCustomizer gracefulShutdownJettyConnectorCustomizer(ApplicationContext applicationContext, GracefulShutdownJettyProperties properties) {
        return new GracefulShutdownJettyConnectorCustomizer(applicationContext, properties);
    }

    @Bean
    @ConditionalOnMissingBean
    public ReactiveWebHeaderProcessor reactiveWebHeaderProcessor() {
        return new ReactiveWebHeaderProcessor();
    }

    @Bean
    @ConditionalOnMissingBean
    public ReactiveWebContextFilter reactiveWebContextFilter(DispatcherRequestCustomizer<RequestMessageDefinition.ProduceValues> dispatcherRequestCustomizer) {
        return new ReactiveWebContextFilter(dispatcherRequestCustomizer);
    }

    @Bean
    @ConditionalOnMissingBean
    public ServletWebContextListener servletWebContextListener(DispatcherRequestCustomizer<RequestMessageDefinition.ProduceValues> dispatcherRequestCustomizer) {
        return new ServletWebContextListener(dispatcherRequestCustomizer);
    }
}
