package io.genhai.client.rsocket.client;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author yugenhai
 */
@SpringBootApplication
public class MusicClientApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .main(MusicClientApplication.class)
                .sources(MusicClientApplication.class)
                .profiles("client")
                .run(args);
    }
}
