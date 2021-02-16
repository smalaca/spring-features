package com.smalaca.rentalapp.infrastructure.producer;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.stream.IntStream;

@Component
class OnStartupMessagesProducer {
    private final KafkaMessageProducer producer;

    OnStartupMessagesProducer(KafkaMessageProducer producer) {
        this.producer = producer;
    }

    @Async
    @EventListener
    public void publishEvents(ContextRefreshedEvent event) {
        IntStream.range(0, 20).forEach(index -> {
            producer.sendHotelRoom("Hotel Room " + index);
            producer.send(new ApartmentDto(index));
        });
    }
}
