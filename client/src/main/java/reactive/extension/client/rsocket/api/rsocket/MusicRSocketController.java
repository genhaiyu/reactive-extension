package reactive.extension.client.rsocket.api.rsocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import reactive.extension.client.rsocket.model.MusicData;
import reactive.extension.client.rsocket.model.MusicDataRequest;
import reactive.extension.client.rsocket.service.MusicService;
import reactor.core.publisher.Flux;

// @Controller
public class MusicRSocketController {

    private final MusicService musicService;

    public MusicRSocketController(MusicService musicService) {
        this.musicService = musicService;
    }

    @MessageMapping("music.author")
    public Flux<MusicData> getByAuthor(MusicDataRequest request) {
        return musicService.getByAuthor(request.getAuthor());
    }
}
