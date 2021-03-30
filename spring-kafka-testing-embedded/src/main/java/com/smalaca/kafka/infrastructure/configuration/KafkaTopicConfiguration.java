package com.smalaca.kafka.infrastructure.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfiguration {
    @Bean
    public NewTopic topic(@Value("${test.topic}") String topicName) {
        return new NewTopic(topicName, 1, (short) 1);
    }
}
