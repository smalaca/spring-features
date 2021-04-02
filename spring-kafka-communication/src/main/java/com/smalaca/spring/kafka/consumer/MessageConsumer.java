package com.smalaca.spring.kafka.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
class MessageConsumer {
    @KafkaListener(
            topics = {"${topics.topic-one}", "${topics.topic-two}", "${topics.topic-three}", "${topics.topic-four}"})
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

    @KafkaListener(topics = {"${topics.topic-five}"}, groupId = "group-three")
    public void consumerFour(
            @Payload String message,
            @Header("my-header") String myHeader,
            @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partitionId,
            @Headers MessageHeaders messageHeaders) {
        System.out.println("group-three; " + message + "; " + partitionId);
        System.out.println("HEADERS with my header: " + myHeader);
        messageHeaders.forEach((s, s2) -> System.out.println(s + ": " + s2));
    }
}
