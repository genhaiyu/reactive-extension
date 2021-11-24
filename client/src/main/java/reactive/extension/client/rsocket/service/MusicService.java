package reactive.extension.client.rsocket.service;

import reactive.extension.client.rsocket.model.MusicData;
import reactive.extension.client.rsocket.repository.MusicRepository;
import reactive.extension.client.rsocket.server.MusicDataRepository;
import reactor.core.publisher.Flux;

import java.time.Duration;

// @Service
public class MusicService {

    private final MusicRepository musicRepository;
    private final MusicDataRepository musicDataRepository;

    public MusicService(MusicRepository musicRepository, MusicDataRepository musicDataRepository) {
        this.musicRepository = musicRepository;
        this.musicDataRepository = musicDataRepository;
    }

    public Flux<MusicData> getByAuthor(String author) {

        return Flux
                .interval(Duration.ZERO,
                        Duration.ofSeconds(1))
                .flatMap(i -> musicRepository.getMusicData(author));
    }


    public Flux<MusicData> getAllMusicData(String author) {
        return
                Flux.interval(Duration.ofMillis(100))
                        .map(l -> {

                            MusicData musicData = new MusicData();
                            musicData.setSongInfo("happy song");
                            musicData.setAuthor(author);
                            return musicData;
                        }).take(2);
    }

}
