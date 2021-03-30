package com.smalaca.kafka.infrastructure.producer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageProducer {
    private final String topicName;
    private final KafkaTemplate<String, String> template;

    MessageProducer(@Value("${test.topic}") String topicName, KafkaTemplate<String, String> template) {
        this.topicName = topicName;
        this.template = template;
    }

    public void send(String payload) {
        template.send(topicName, payload);
    }
}
