package com.smalaca.spring.kafka.producer;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Consumer;

@Component
@ConfigurationProperties(prefix = "topics")
public class KafkaTopics {
    private Map<String, String> simple;

    void setSimple(Map<String, String> simple) {
        this.simple = simple;
    }

    void forEach(Consumer<String> consumer) {
        simple.values().forEach(consumer);
    }
}
