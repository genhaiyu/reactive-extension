package io.genhai.client.rsocket.server;

import io.genhai.client.rsocket.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * Message frames
 *
 * @author yugenhai
 */
@Slf4j
@Controller
public class MessageRSocketController {

    private static final String SERVER = "Server";
    private static final String RESPONSE = "Response";
    private static final String STREAM = "Stream";
    private static final String CHANNEL = "Channel";

    /**
     * yuzhilindeMacBook-Pro:coral yuzhilin$ java -jar rsc.jar --debug --request --data "{\"origin\":\"Client\",\"interaction\":\"Request\"}" --route request-response tcp://localhost:8082
     * 2020-04-26 22:26:26.092 DEBUG --- [actor-tcp-nio-1] i.r.FrameLogger : sending ->
     * Frame => Stream ID: 1 Type: REQUEST_RESPONSE Flags: 0b100000000 Length: 69
     * Metadata:
     * +-------------------------------------------------+
     * |  0  1  2  3  4  5  6  7  8  9  a  b  c  d  e  f |
     * +--------+-------------------------------------------------+----------------+
     * |00000000| 10 72 65 71 75 65 73 74 2d 72 65 73 70 6f 6e 73 |.request-respons|
     * |00000010| 65                                              |e               |
     * +--------+-------------------------------------------------+----------------+
     * Data:
     * +-------------------------------------------------+
     * |  0  1  2  3  4  5  6  7  8  9  a  b  c  d  e  f |
     * +--------+-------------------------------------------------+----------------+
     * |00000000| 7b 22 6f 72 69 67 69 6e 22 3a 22 43 6c 69 65 6e |{"origin":"Clien|
     * |00000010| 74 22 2c 22 69 6e 74 65 72 61 63 74 69 6f 6e 22 |t","interaction"|
     * |00000020| 3a 22 52 65 71 75 65 73 74 22 7d                |:"Request"}     |
     * +--------+-------------------------------------------------+----------------+
     * 2020-04-26 22:26:26.294 DEBUG --- [actor-tcp-nio-1] i.r.FrameLogger : receiving ->
     * Frame => Stream ID: 1 Type: NEXT_COMPLETE Flags: 0b1100000 Length: 81
     * Data:
     * +-------------------------------------------------+
     * |  0  1  2  3  4  5  6  7  8  9  a  b  c  d  e  f |
     * +--------+-------------------------------------------------+----------------+
     * |00000000| 7b 22 6f 72 69 67 69 6e 22 3a 22 53 65 72 76 65 |{"origin":"Serve|
     * |00000010| 72 22 2c 22 69 6e 74 65 72 61 63 74 69 6f 6e 22 |r","interaction"|
     * |00000020| 3a 22 52 65 73 70 6f 6e 73 65 22 2c 22 69 6e 64 |:"Response","ind|
     * |00000030| 65 78 22 3a 30 2c 22 63 72 65 61 74 65 64 22 3a |ex":0,"created":|
     * |00000040| 31 35 38 37 39 36 38 31 30 34 7d                |1587911186}     |
     * +--------+-------------------------------------------------+----------------+
     * {"origin":"Server","interaction":"Response","index":0,"created":1587911186}
     *
     * @param request
     * @return Message
     */
    @MessageMapping("request-response")
    Message requestResponse(final Message request) {
        log.info("Received request-response request: {}", request);
        return new Message(SERVER, RESPONSE);
    }


    /**
     * yuzhilindeMacBook-Pro:coral yuzhilin$ java -jar rsc.jar --debug --request --data "{\"origin\":\"Client\",\"interaction\":\"Request\"}" --route no-response tcp://localhost:8082
     * 2020-04-27 14:30:50.453 DEBUG --- [actor-tcp-nio-1] i.r.FrameLogger : sending ->
     * Frame => Stream ID: 1 Type: REQUEST_RESPONSE Flags: 0b100000000 Length: 64
     * Metadata:
     *          +-------------------------------------------------+
     *          |  0  1  2  3  4  5  6  7  8  9  a  b  c  d  e  f |
     * +--------+-------------------------------------------------+----------------+
     * |00000000| 0b 6e 6f 2d 72 65 73 70 6f 6e 73 65             |.no-response    |
     * +--------+-------------------------------------------------+----------------+
     * Data:
     *          +-------------------------------------------------+
     *          |  0  1  2  3  4  5  6  7  8  9  a  b  c  d  e  f |
     * +--------+-------------------------------------------------+----------------+
     * |00000000| 7b 22 6f 72 69 67 69 6e 22 3a 22 43 6c 69 65 6e |{"origin":"Clien|
     * |00000010| 74 22 2c 22 69 6e 74 65 72 61 63 74 69 6f 6e 22 |t","interaction"|
     * |00000020| 3a 22 52 65 71 75 65 73 74 22 7d                |:"Request"}     |
     * +--------+-------------------------------------------------+----------------+
     * 2020-04-27 14:30:50.599 DEBUG --- [actor-tcp-nio-1] i.r.FrameLogger : receiving ->
     * Frame => Stream ID: 1 Type: COMPLETE Flags: 0b1000000 Length: 6
     * Data:
     *
     * @param request
     */
    @MessageMapping("no-response")
    public void noResponse(final Message request){
        log.info("Received no-response request: {}", request);
    }


    @MessageMapping({"stream"})
    Flux<Message> stream(final Message request) {
        log.info("Received stream request: {}", request);
        return Flux
                // create a new indexed Flux emitting one element every second
                .interval(Duration.ofSeconds(1))
                // create a Flux of new Messages using the indexed Flux
                .map(index -> new Message(SERVER, STREAM, index))
                // use the Flux logger to output each flux event
                .log();
    }

    @MessageMapping("channel")
    Flux<Message> channel(final Flux<Duration> settings) {
        return settings
                .doOnNext(setting -> log.info("\nFrequency setting is {} second(s).\n", setting.getSeconds()))
                .switchMap(setting -> Flux.interval(setting)
                        .map(index -> new Message(SERVER, CHANNEL, index)))
                .log();
    }

}
