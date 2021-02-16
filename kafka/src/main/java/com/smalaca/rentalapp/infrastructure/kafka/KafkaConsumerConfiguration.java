package com.smalaca.rentalapp.infrastructure.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfiguration {
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> groupOneKafkaListenerContainerFactory(
            @Value("${kafka.bootstrapAddress}") String bootstrapAddress, @Value("${kafka.group.one}") String groupName) {
        return kafkaListenerContainerFactory(bootstrapAddress, groupName);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> groupTwoKafkaListenerContainerFactory(
            @Value("${kafka.bootstrapAddress}") String bootstrapAddress, @Value("${kafka.group.two}") String groupName) {
        return kafkaListenerContainerFactory(bootstrapAddress, groupName);
    }

    private ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory(
            String bootstrapAddress, String groupId) {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory(bootstrapAddress, groupId));

        return factory;
    }

    private ConsumerFactory<String, String> consumerFactory(String bootstrapAddress, String groupId) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props);
    }
}
