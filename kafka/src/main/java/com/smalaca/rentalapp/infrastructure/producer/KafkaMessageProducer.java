package com.smalaca.rentalapp.infrastructure.producer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaMessageProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaTemplate<String, ApartmentDto> kafkaApartmentDtoTemplate;
    private final String apartmentTopicName;
    private final String hotelRoomTopicName;

    KafkaMessageProducer(
            KafkaTemplate<String, String> kafkaTemplate,
            KafkaTemplate<String, ApartmentDto> kafkaApartmentDtoTemplate,
            @Value("${kafka.topic.apartment}") String apartmentTopicName,
            @Value("${kafka.topic.hotel-room}") String hotelRoomTopicName) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaApartmentDtoTemplate = kafkaApartmentDtoTemplate;
        this.apartmentTopicName = apartmentTopicName;
        this.hotelRoomTopicName = hotelRoomTopicName;
    }

    public void sendHotelRoom(String message) {
        kafkaTemplate.send(hotelRoomTopicName, message);
    }

    void send(ApartmentDto apartmentDto) {
        kafkaApartmentDtoTemplate.send(apartmentTopicName, apartmentDto);
    }
}
