package reactive.extension.client.rsocket.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactive.extension.client.rsocket.model.MusicData;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.stream.Stream;

@Slf4j
@Component
public class MusicDataRepository {


    public Flux<MusicData> getAllMusicData(String author) {
        return Flux.fromStream(Stream.generate(() -> getMusicDataResponse(author)))
                .log()
                .delayElements(Duration.ofSeconds(5));
    }


    public Mono<MusicData> getOneMusicData(String author) {
        return Mono.just(getMusicDataResponse(author));
    }

    public void addMusic(MusicData musicData) {
        log.info("New music data: {}", musicData);
    }


    public MusicData getMusicDataResponse(String author) {
        return new MusicData("happysong", author);
    }
}
