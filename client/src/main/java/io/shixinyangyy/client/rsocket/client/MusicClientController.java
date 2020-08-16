package io.shixinyangyy.client.rsocket.client;

import io.shixinyangyy.client.rsocket.model.MusicData;
import io.shixinyangyy.client.rsocket.model.MusicDataRequest;
import org.reactivestreams.Publisher;
import org.springframework.http.MediaType;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Shixinyangyy
 */
@RestController
public class MusicClientController {

    private final RSocketRequester rSocketRequester;

    public MusicClientController(RSocketRequester rSocketRequester) {
        this.rSocketRequester = rSocketRequester;
    }

    @GetMapping(value = "/current/{song}")
    public Publisher<MusicData> current(@PathVariable("song") String song) {
        return rSocketRequester.route("currentMarketData")
                .data(new MusicDataRequest(song))
                .retrieveMono(MusicData.class);
    }

    @GetMapping(value = "/all/{song}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Publisher<MusicData> all(@PathVariable("song") String song) {
        return rSocketRequester.route("allMusicData")
                .data(new MusicDataRequest(song))
                .retrieveFlux(MusicData.class);
    }

    @GetMapping(value = "/addMusicData")
    public Publisher<Void> add() {
        return rSocketRequester.route("addMusicData")
                .data(getMusicData())
                .send();
    }

    private MusicData getMusicData() {
        return new MusicData("happyMusic", "author1");
    }

}
