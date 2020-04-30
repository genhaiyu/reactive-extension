package io.genhai.client.rsocket.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * @author yugenhai
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    // where a message came from
    private String origin;
    // which messaging style it was intended to use
    private String interaction;
    private long index;
    private long created = Instant.now().getEpochSecond();

    public Message(String origin, String interaction) {
        this.origin = origin;
        this.interaction = interaction;
        this.index = 0;
    }

    public Message(String origin, String interaction, long index) {
        this.origin = origin;
        this.interaction = interaction;
        this.index = index;
    }
}
