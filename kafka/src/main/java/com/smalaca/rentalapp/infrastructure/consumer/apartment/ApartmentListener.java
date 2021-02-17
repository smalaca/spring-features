package com.smalaca.rentalapp.infrastructure.consumer.apartment;

import com.smalaca.rentalapp.infrastructure.producer.ApartmentDto;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ApartmentListener {
    @KafkaListener(
            topics = "${kafka.topic.apartment}",
            groupId = "${kafka.group.one}",
            containerFactory = "apartmentDtoOddKafkaListenerContainerFactory")
    public void listenGroupOneOdd(ApartmentDto apartmentDto) {
        System.out.println("APARTMENT ODD: Group One: " + apartmentDto);
    }

    @KafkaListener(
            topics = "${kafka.topic.apartment}",
            groupId = "${kafka.group.two}",
            containerFactory = "apartmentDtoEvenKafkaListenerContainerFactory")
    public void listenGroupTwo(ApartmentDto apartmentDto) {
        System.out.println("APARTMENT EVEN: Group Two: " + apartmentDto);
    }
}
