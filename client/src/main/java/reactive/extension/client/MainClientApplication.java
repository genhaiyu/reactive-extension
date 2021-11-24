package reactive.extension.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactive.extension.client.rsocket.client.MusicClientApplication;
import reactive.extension.client.rsocket.server.MusicServerApplication;

/**
 * First run the {@link MusicServerApplication}
 * <p>
 * then the {@link MusicClientApplication}
 */
@SpringBootApplication
public class MainClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainClientApplication.class);
    }
}
