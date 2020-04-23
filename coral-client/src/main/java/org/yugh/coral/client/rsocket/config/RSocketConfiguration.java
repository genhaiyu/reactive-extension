package org.yugh.coral.client.rsocket.config;

import org.springframework.boot.autoconfigure.rsocket.RSocketProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import reactor.core.publisher.Mono;

import java.net.URI;

@Configuration
public class RSocketConfiguration {

    @Bean
    public Mono<RSocketRequester> rSocketRequester(RSocketStrategies rSocketStrategies,
                                                   RSocketProperties rSocketProps,
                                                   ServerProperties serverProps) {
        return RSocketRequester.builder().rsocketStrategies(rSocketStrategies)
                .connectWebSocket(getURI(rSocketProps, serverProps));
    }

    private URI getURI(RSocketProperties rSocketProps, ServerProperties serverProps) {
        String protocol = serverProps.getSsl() != null ? "wss" : "ws";
        return URI.create(String.format("%s://localhost:%d%s", protocol,
                rSocketProps.getServer().getPort(), rSocketProps.getServer().getMappingPath()));
    }

}
