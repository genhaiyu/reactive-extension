package io.shixinyangyy.client.rsocket.api.rsocket;

import io.shixinyangyy.client.rsocket.model.MusicData;
import io.shixinyangyy.client.rsocket.model.MusicDataRequest;
import io.shixinyangyy.client.rsocket.service.MusicService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import reactor.core.publisher.Flux;

/**
 * @author Shixinyangyy
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
