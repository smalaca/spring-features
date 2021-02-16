package com.smalaca.rentalapp.infrastructure.kafka;

import com.smalaca.rentalapp.infrastructure.producer.ApartmentDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

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
        Map<String, Object> properties = properties(bootstrapAddress, groupId);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        return new DefaultKafkaConsumerFactory<>(properties);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ApartmentDto> groupOneKafkaListenerContainerFactoryApartmentDto(
            @Value("${kafka.bootstrapAddress}") String bootstrapAddress, @Value("${kafka.group.one}") String groupName) {
        return kafkaListenerContainerFactoryApartmentDto(bootstrapAddress, groupName);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ApartmentDto> groupTwoKafkaListenerContainerFactoryApartmentDto(
            @Value("${kafka.bootstrapAddress}") String bootstrapAddress, @Value("${kafka.group.two}") String groupName) {
        return kafkaListenerContainerFactoryApartmentDto(bootstrapAddress, groupName);
    }

    private ConcurrentKafkaListenerContainerFactory<String, ApartmentDto> kafkaListenerContainerFactoryApartmentDto(
            String bootstrapAddress, String groupId) {
        ConcurrentKafkaListenerContainerFactory<String, ApartmentDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryApartmentDto(bootstrapAddress, groupId));

        return factory;
    }

    private ConsumerFactory<String, ApartmentDto> consumerFactoryApartmentDto(String bootstrapAddress, String groupId) {
        Map<String, Object> properties = properties(bootstrapAddress, groupId);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        properties.put(JsonDeserializer.TRUSTED_PACKAGES, "com.smalaca.rentalapp.infrastructure.producer");

        return new DefaultKafkaConsumerFactory<>(properties);
    }

    private Map<String, Object> properties(String bootstrapAddress, String groupId) {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return properties;
    }
}
