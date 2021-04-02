package com.smalaca.spring.kafka.producer;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.stream.IntStream;

@Component
class OnStartupProducer {
    private final KafkaTemplate<String, String> template;
    private final KafkaTopics kafkaTopics;

    OnStartupProducer(KafkaTemplate<String, String> template, KafkaTopics kafkaTopics) {
        this.template = template;
        this.kafkaTopics = kafkaTopics;
    }

    @Async
    @EventListener
    public void listen(ContextRefreshedEvent event) {
        IntStream.range(0, 10).forEach(index -> {
            kafkaTopics.forEach(topicName -> template.send(topicName, message(index, topicName)));
        });
    }

    private String message(int index, String topicName) {
        return "Topic: " + topicName + ", Message: This is crazy " + index;
    }
}
