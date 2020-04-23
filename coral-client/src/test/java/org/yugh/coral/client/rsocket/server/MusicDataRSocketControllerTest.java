package org.yugh.coral.client.rsocket.server;

import io.rsocket.RSocket;
import io.rsocket.RSocketFactory;
import io.rsocket.frame.decoder.PayloadDecoder;
import io.rsocket.transport.netty.client.TcpClientTransport;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.MimeTypeUtils;
import org.yugh.coral.client.rsocket.model.MusicData;
import org.yugh.coral.client.rsocket.model.MusicDataRequest;

import java.time.Duration;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@TestPropertySource(properties = "spring.rsocket.server.port=7071")
public class MusicDataRSocketControllerTest {

    @Autowired
    private RSocketRequester rSocketRequester;

    @SpyBean
    private MusicDataController musicDataController;

    @Test
    public void whenGetsFireAndForget_ThenReturnsNoResponse() throws InterruptedException {
        final MusicData musicData = new MusicData("happyMusic", "author1");
        rSocketRequester.route("addMusicData")
                .data(musicData)
                .send()
                .block(Duration.ofSeconds(10));

        sleepForProcessing();
        verify(musicDataController).addMusicData(any());
    }

    @Test
    public void whenGetsRequest_ThenReturnsResponse() throws InterruptedException {
        final MusicDataRequest request = new MusicDataRequest("happyMusic");
        rSocketRequester.route("currentMusicData")
                .data(request)
                .send()
                .block(Duration.ofSeconds(10));

        sleepForProcessing();
        verify(musicDataController).currentMusicData(any());
    }

    @Test
    public void whenGetsRequest_ThenReturnsStream() throws InterruptedException {
        final MusicDataRequest request = new MusicDataRequest("happyMusic");
        rSocketRequester.route("allMusicData")
                .data(request)
                .send()
                .block(Duration.ofSeconds(10));

        sleepForProcessing();
        verify(musicDataController).allMusicData(any());
    }

    private void sleepForProcessing() throws InterruptedException {
        Thread.sleep(1000);
    }

    @TestConfiguration
    public static class ClientConfiguration {

        @Bean
        @Lazy
        public RSocket rSocket() {
            return RSocketFactory.connect()
                    .mimeType(MimeTypeUtils.APPLICATION_JSON_VALUE, MimeTypeUtils.APPLICATION_JSON_VALUE)
                    .frameDecoder(PayloadDecoder.ZERO_COPY)
                    .transport(TcpClientTransport.create(7000))
                    .start()
                    .block();
        }

        @Bean
        @Lazy
        RSocketRequester rSocketRequester(RSocketStrategies rSocketStrategies) {
            return RSocketRequester.wrap(rSocket(), MimeTypeUtils.APPLICATION_JSON, MimeTypeUtils.APPLICATION_JSON, rSocketStrategies);
        }
    }
}
