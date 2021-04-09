package com.smalaca.spring.kafka.producer;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Consumer;

@Component
@ConfigurationProperties(prefix = "topics")
public class KafkaTopics {
    private Map<String, String> simple;
    private Map<String, String> routing;

    void setSimple(Map<String, String> simple) {
        this.simple = simple;
    }

    void setRouting(Map<String, String> routing) {
        this.routing = routing;
    }

    void forEach(Consumer<String> consumer) {
        simple.values().forEach(consumer);
    }

    String routingString() {
        return routing.get("one");
    }

    String routingUser() {
        return routing.get("two");
    }
}
