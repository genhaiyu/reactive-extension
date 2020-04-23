package org.yugh.coral.client.rsocket.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketRequester.RequestSpec;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.yugh.coral.client.rsocket.model.MusicData;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;

@RunWith(SpringRunner.class)
@WebFluxTest(value = MusicClientApplication.class)
public class MusicDataRSocketClientControllerTest {

    @Autowired
    private WebTestClient testClient;

    @MockBean
    private RSocketRequester rSocketRequester;

    @Mock
    private RequestSpec requestSpec;

    @Test
    public void whenInitiatesRequest_ThenGetsResponse() throws Exception {
        when(rSocketRequester.route("currentMusicData")).thenReturn(requestSpec);
        when(requestSpec.data(any())).thenReturn(requestSpec);
        MusicData musicData = new MusicData("happyMusic", "author1");
        when(requestSpec.retrieveMono(MusicData.class)).thenReturn(Mono.just(musicData));

        testClient.get()
                  .uri("/current/{song}", "happyMusic")
                  .exchange()
                  .expectStatus()
                  .isOk()
                  .expectBody(MusicData.class)
                  .isEqualTo(musicData);
    }

    @Test
    public void whenInitiatesFireAndForget_ThenGetsNoResponse() throws Exception {
        when(rSocketRequester.route("addMusicData")).thenReturn(requestSpec);
        when(requestSpec.data(any())).thenReturn(requestSpec);
        when(requestSpec.send()).thenReturn(Mono.empty());

        testClient.get()
                  .uri("/addMusicData")
                  .exchange()
                  .expectStatus()
                  .isOk()
                  .expectBody(Void.class);
    }

    @Test
    public void whenInitiatesRequest_ThenGetsStream() throws Exception {
        when(rSocketRequester.route("allMusicData")).thenReturn(requestSpec);
        when(requestSpec.data(any())).thenReturn(requestSpec);
        MusicData firstMusicData = new MusicData("happyMusic", "author1");
        MusicData secondMusicData = new MusicData("happyMusic", "author2");
        when(requestSpec.retrieveFlux(MusicData.class)).thenReturn(Flux.just(firstMusicData, secondMusicData));

        FluxExchangeResult<MusicData> result = testClient.get()
                                                          .uri("/all/{song}", "happyMusic")
                                                          .accept(TEXT_EVENT_STREAM)
                                                          .exchange()
                                                          .expectStatus()
                                                          .isOk()
                                                          .returnResult(MusicData.class);
        Flux<MusicData> marketDataFlux = result.getResponseBody();
        StepVerifier.create(marketDataFlux)
                    .expectNext(firstMusicData)
                    .expectNext(secondMusicData)
                    .thenCancel()
                    .verify();
    }
}
