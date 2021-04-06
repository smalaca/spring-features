package com.smalaca.spring.kafka.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicsConfiguration {
    @Bean
    public NewTopic topicOne(@Value("${topics.simple.topic-one}") String topicName) {
        return TopicBuilder.name(topicName).build();
    }

    @Bean
    public NewTopic topicTwo(@Value("${topics.simple.topic-two}") String topicName) {
        return TopicBuilder.name(topicName).build();
    }

    @Bean
    public NewTopic topicThree(@Value("${topics.simple.topic-three}") String topicName) {
        return TopicBuilder.name(topicName).build();
    }

    @Bean
    public NewTopic topicFour(@Value("${topics.simple.topic-four}") String topicName) {
        return TopicBuilder.name(topicName).build();
    }

    @Bean
    public NewTopic topicFive(@Value("${topics.with-header.topic-five}") String topicName) {
        return TopicBuilder.name(topicName).build();
    }
}
