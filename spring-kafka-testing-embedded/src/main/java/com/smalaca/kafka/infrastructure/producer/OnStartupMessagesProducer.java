package com.smalaca.kafka.infrastructure.producer;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.stream.IntStream;

@Component
class OnStartupMessagesProducer {
    private final MessageProducer producer;

    OnStartupMessagesProducer(MessageProducer producer) {
        this.producer = producer;
    }

    @Async
    @EventListener
    public void publishEvents(ContextRefreshedEvent event) {
        IntStream.range(0, 10).forEach(index -> producer.send("Payload sent: " + index));
    }
}
