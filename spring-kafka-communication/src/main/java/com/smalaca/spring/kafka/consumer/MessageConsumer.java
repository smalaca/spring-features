package com.smalaca.spring.kafka.consumer;

import com.smalaca.spring.kafka.dto.UserDto;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
class MessageConsumer {
    @KafkaListener(
            topics = {
                    "${topics.simple.topic-one}", "${topics.simple.topic-two}",
                    "${topics.simple.topic-three}", "${topics.simple.topic-four}"}
    )
    @SendTo("${topics.send-to.topic-six}")
    public String consumeOne(String message) {
        System.out.println("DEFAULT; " + message);
        return "Consumer One: " + message;
    }

    @KafkaListener(topics = {"${topics.simple.topic-one}"}, groupId = "group-two")
    @SendTo("${topics.send-to.topic-six}")
    public String consumerTwo(String message) {
        System.out.println("group-two; consumer-one; " + message);
        return "Consumer Two: " + message;
    }

    @KafkaListener(topics = {"${topics.simple.topic-one}"}, groupId = "group-two")
    @SendTo("${topics.send-to.topic-six}")
    public String consumerThree(String message) {
        System.out.println("group-two; consumer-two; " + message);
        return "Consumer Three: " + message;
    }

    @KafkaListener(topics = {"${topics.with-header.topic-five}"}, groupId = "group-three")
    public void consumerFour(
            @Payload String message,
            @Header("my-header") String myHeader,
            @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partitionId,
            @Headers MessageHeaders messageHeaders) {
        System.out.println("group-three; " + message + "; " + partitionId);
        System.out.println("HEADERS with my header: " + myHeader);
        messageHeaders.forEach((s, s2) -> System.out.println(s + ": " + s2));
    }

    @KafkaListener(topics = {"${topics.send-to.topic-six}"}, groupId = "group-four")
    public void consumerFive(String message) {
        System.out.println("REPLIED WITH: " + message);
    }

    @KafkaListener(topics = {"${topics.routing.one}"})
    public void consumerSix(String message) {
        System.out.println("ROUTING: Consumer Six: " + message);
    }

    @KafkaListener(topics = {"${topics.routing.two}"}, containerFactory = "userDtoKafkaListenerContainerFactory")
    public void consumerSeven(UserDto user) {
        System.out.println("ROUTING: Consumer Six: " + user);
    }
}
