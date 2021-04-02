package com.smalaca.spring.kafka.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
class MessageConsumer {
    @KafkaListener(
            topics = {"${topics.topic-one}", "${topics.topic-two}", "${topics.topic-three}", "${topics.topic-four}", "${topics.topic-five}"})
    public void consume(String message) {
        System.out.println(message);
    }
}
