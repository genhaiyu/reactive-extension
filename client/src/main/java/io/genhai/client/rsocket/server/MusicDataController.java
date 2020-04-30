package io.genhai.client.rsocket.server;

import io.genhai.client.rsocket.model.MusicData;
import io.genhai.client.rsocket.model.MusicDataRequest;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * RSocket
 *
 * @author yugenhai
 */
@Controller
public class MusicDataController {


    private final MusicDataRepository musicDataRepository;

    public MusicDataController(MusicDataRepository musicDataRepository){
        this.musicDataRepository = musicDataRepository;
    }


    @MessageMapping("currentMusicData")
    public Mono<MusicData> currentMusicData(MusicDataRequest request){
        return musicDataRepository.getOneMusicData(request.getAuthor());
    }

    @MessageMapping("allMusicData")
    public Flux<MusicData> allMusicData(MusicDataRequest request){
        return musicDataRepository.getAllMusicData(request.getAuthor());
    }

    @MessageMapping("addMusicData")
    public Mono<Void> addMusicData(MusicData musicData){
        musicDataRepository.addMusic(musicData);
        return Mono.empty();
    }


    @MessageExceptionHandler
    public Mono<MusicData> handleException(Exception e){
        return Mono.just(MusicData.musicDataException(e));
    }


}
