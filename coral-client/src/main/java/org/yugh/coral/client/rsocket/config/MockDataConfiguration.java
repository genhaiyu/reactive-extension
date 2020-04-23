package org.yugh.coral.client.rsocket.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.yugh.coral.client.rsocket.model.MusicData;
import org.yugh.coral.client.rsocket.repository.MusicRepository;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Configuration
public class MockDataConfiguration {

    @Autowired
    private MusicRepository repository;

    private static final MusicData[] musicData = new MusicData[] {
            new MusicData("happy song1", "author1"),
            new MusicData("happy song2", "author2"),
            new MusicData("happy song3", "author3")
    };

    //@EventListener(ApplicationReadyEvent.class)
    public void loadData() {
        Flux.interval(Duration.ofSeconds(5))
        .map(i -> musicData[(int) (i % musicData.length)])
        .flatMap(tweet -> repository.saveMusic(MusicData.of(tweet)))
        .subscribe();
    }
}
