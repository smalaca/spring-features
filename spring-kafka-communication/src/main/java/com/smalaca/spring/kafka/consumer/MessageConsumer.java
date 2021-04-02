package com.smalaca.spring.kafka.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
class MessageConsumer {
    @KafkaListener(
            topics = {"${topics.topic-one}", "${topics.topic-two}", "${topics.topic-three}", "${topics.topic-four}", "${topics.topic-five}"})
    public void consumeOne(String message) {
        System.out.println("DEFAULT; " + message);
    }

    @KafkaListener(topics = {"${topics.topic-one}"}, groupId = "group-two")
    public void consumerTwo(String message) {
        System.out.println("group-one; " + message);
    }

    @KafkaListener(topics = {"${topics.topic-one}"}, groupId = "group-two")
    public void consumerThree(String message) {
        System.out.println("group-two; " + message);
    }
}
