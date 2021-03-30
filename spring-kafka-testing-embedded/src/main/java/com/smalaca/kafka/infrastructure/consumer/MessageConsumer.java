package com.smalaca.kafka.infrastructure.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {
    private String payload = null;

    @KafkaListener(topics = "${test.topic}")
    public void receive(String payload) {
        this.payload = payload;
    }

    public String getPayload() {
        return payload;
    }
}
