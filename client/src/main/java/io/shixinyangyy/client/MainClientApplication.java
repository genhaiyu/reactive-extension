package io.shixinyangyy.client;

import io.shixinyangyy.client.rsocket.client.MusicClientApplication;
import io.shixinyangyy.client.rsocket.server.MusicServerApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * First run {@link MusicServerApplication}
 * Next run {@link MusicClientApplication}
 *
 * @author Shixinyangyy
 */
@SpringBootApplication
public class MainClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainClientApplication.class);
    }
}
