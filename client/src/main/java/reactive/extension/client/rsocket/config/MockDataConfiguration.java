package reactive.extension.client.rsocket.config;

import reactive.extension.client.rsocket.model.MusicData;
import reactive.extension.client.rsocket.repository.MusicRepository;
import reactor.core.publisher.Flux;

import java.time.Duration;

// @Component
public class MockDataConfiguration {

    // @Autowired
    private MusicRepository repository;

    private static final MusicData[] musicData = new MusicData[]{
            new MusicData("happy song1", "author1"),
            new MusicData("happy song2", "author2"),
            new MusicData("happy song3", "author3")
    };

    // @EventListener(ApplicationReadyEvent.class)
    public void loadData() {
        Flux.interval(Duration.ofSeconds(5))
                .map(i -> musicData[(int) (i % musicData.length)])
                .flatMap(tweet -> repository.saveMusic(MusicData.of(tweet)))
                .subscribe();
    }
}
