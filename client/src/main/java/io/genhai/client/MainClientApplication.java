package io.genhai.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * First run {@link io.genhai.client.rsocket.server.MusicServerApplication}
 * Next run {@link io.genhai.client.rsocket.client.MusicClientApplication}
 *
 * @author yugenhai
 */
@SpringBootApplication
public class MainClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainClientApplication.class);
    }
}
