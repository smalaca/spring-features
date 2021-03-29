package com.smalaca.rentalapp.infrastructure.kafka;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfiguration {
    @Bean
    public KafkaAdmin kafkaAdmin(@Value("${kafka.bootstrapAddress}") String bootstrapAddress) {
        Map<String, Object> properties = new HashMap<>();
        properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);

        return new KafkaAdmin(properties);
    }

    @Bean
    public NewTopic apartmentTopic(@Value("${kafka.topic.apartment}") String topicName) {
        return newTopic(topicName);
    }

    @Bean
    public NewTopic hotelRoomTopic(@Value("${kafka.topic.hotel-room}") String topicName) {
        return newTopic(topicName);
    }

    private NewTopic newTopic(String topicName) {
        return new NewTopic(topicName, 1, (short) 1);
    }
}
