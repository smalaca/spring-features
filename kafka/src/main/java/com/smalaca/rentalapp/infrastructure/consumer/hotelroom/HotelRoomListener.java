package com.smalaca.rentalapp.infrastructure.consumer.hotelroom;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class HotelRoomListener {
    @KafkaListener(topics = "${kafka.topic.hotel-room}", groupId = "${kafka.group.one}")
    public void listenGroupOne(String message) {
        System.out.println("HOTEL ROOM: Group One: " + message);
    }

    @KafkaListener(topics = "${kafka.topic.hotel-room}", groupId = "${kafka.group.two}")
    public void listenGroupTwo(String message) {
        System.out.println("HOTEL ROOM: Group Two: " + message);
    }
}
