package reactive.extension.client.rsocket.server;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class MusicServerApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .main(MusicServerApplication.class)
                .sources(MusicServerApplication.class)
                .profiles("server")
                .run(args);
    }
}
