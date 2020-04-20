package org.yugh.coral.client.rsocket.server;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author yugenhai
 */
@SpringBootApplication
public class ServerApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .main(ServerApplication.class)
                .sources(ServerApplication.class)
                .profiles("server")
                .run(args);
    }
}
