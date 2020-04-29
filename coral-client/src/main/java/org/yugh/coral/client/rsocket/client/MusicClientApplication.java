package org.yugh.coral.client.rsocket.client;

import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author yugenhai
 */
// @SpringBootApplication
public class MusicClientApplication {

    public static void main1(String[] args) {
        new SpringApplicationBuilder()
                .main(MusicClientApplication.class)
                .sources(MusicClientApplication.class)
                .profiles("client")
                .run(args);
    }
}
