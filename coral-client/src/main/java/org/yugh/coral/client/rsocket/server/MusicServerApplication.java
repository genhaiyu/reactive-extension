package org.yugh.coral.client.rsocket.server;

import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author yugenhai
 */
// @SpringBootApplication
public class MusicServerApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .main(MusicServerApplication.class)
                .sources(MusicServerApplication.class)
                .profiles("server")
                .run(args);
    }
}
