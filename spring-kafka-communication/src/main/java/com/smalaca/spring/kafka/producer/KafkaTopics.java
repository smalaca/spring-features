package com.smalaca.spring.kafka.producer;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Stream;

@Component
@ConfigurationProperties
public class KafkaTopics {
    private Map<String, String> topics;

    void setTopics(Map<String, String> topics) {
        this.topics = topics;
    }

    Stream<String> all() {
        return topics.values().stream();
    }

    void forEach(Consumer<String> consumer) {
        topics.values().forEach(consumer);
    }
}
