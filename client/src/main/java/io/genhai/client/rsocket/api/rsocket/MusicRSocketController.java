package io.genhai.client.rsocket.api.rsocket;

import io.genhai.client.rsocket.model.MusicData;
import io.genhai.client.rsocket.model.MusicDataRequest;
import io.genhai.client.rsocket.service.MusicService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import reactor.core.publisher.Flux;

/**
 * @author yugenhai
 */
// @Controller
public class MusicRSocketController {

    private final MusicService musicService;

    public MusicRSocketController(MusicService musicService){
        this.musicService = musicService;
    }

    @MessageMapping("music.author")
    public Flux<MusicData> getByAuthor(MusicDataRequest request) {
        return musicService.getByAuthor(request.getAuthor());
    }

}
