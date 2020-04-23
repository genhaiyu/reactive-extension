package org.yugh.coral.client.rsocket.api.rsocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.yugh.coral.client.rsocket.model.MusicData;
import org.yugh.coral.client.rsocket.model.MusicDataRequest;
import org.yugh.coral.client.rsocket.service.MusicService;
import reactor.core.publisher.Flux;

/**
 * @author yugenhai
 */
@Controller
public class MusicSocketController {

    private final MusicService musicService;

    public MusicSocketController(MusicService musicService){
        this.musicService = musicService;
    }

    @MessageMapping("music.author")
    public Flux<MusicData> getByAuthor(MusicDataRequest request) {
        return musicService.getByAuthor(request.getAuthor());
    }

}
