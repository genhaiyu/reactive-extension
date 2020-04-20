package org.yugh.coral.client.rsocket.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.yugh.coral.client.rsocket.model.MusicData;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Random;
import java.util.stream.Stream;

/**
 * Query Music Data.
 *
 * @author yugenhai
 */
@Slf4j
@Component
public class MusicDataRepository {

    private final static int SONG_ID_BOUND = 100;

    private final Random random = new Random();


    public Flux<MusicData> getAllMusicData(String song) {
        return Flux.fromStream(Stream.generate(() -> getMusicDataResponse(song)))
                .log()
                .delayElements(Duration.ofSeconds(1));
    }


    public Mono<MusicData> getOneMusicData(String song){
        return Mono.just(getMusicDataResponse(song));
    }

    public void addMusic(MusicData musicData){
        log.info("New music data: {}", musicData);
    }

    public MusicData getMusicDataResponse(String song) {
        return new MusicData(song, random.nextInt(SONG_ID_BOUND));
    }

}
