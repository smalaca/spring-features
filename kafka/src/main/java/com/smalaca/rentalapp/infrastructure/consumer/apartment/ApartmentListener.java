package com.smalaca.rentalapp.infrastructure.consumer.apartment;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ApartmentListener {
    @KafkaListener(topics = "${kafka.topic.apartment}", groupId = "${kafka.group.one}")
    public void listenGroupOne(String message) {
        System.out.println("APARTMENT: Group One: " + message);
    }

    @KafkaListener(topics = "${kafka.topic.apartment}", groupId = "${kafka.group.two}")
    public void listenGroupTwo(String message) {
        System.out.println("APARTMENT: Group Two: " + message);
    }
}
