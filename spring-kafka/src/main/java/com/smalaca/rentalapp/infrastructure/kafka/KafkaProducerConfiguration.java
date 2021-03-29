package com.smalaca.rentalapp.infrastructure.kafka;

import com.smalaca.rentalapp.infrastructure.producer.ApartmentDto;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfiguration {
    @Bean
    public KafkaTemplate<String, String> kafkaTemplate(@Value("${kafka.bootstrapAddress}") String bootstrapAddress) {
        return new KafkaTemplate<>(producerFactory(bootstrapAddress));
    }

    private ProducerFactory<String, String> producerFactory(String bootstrapAddress) {
        Map<String, Object> properties = properties(bootstrapAddress);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return new DefaultKafkaProducerFactory<>(properties);
    }

    @Bean
    public KafkaTemplate<String, ApartmentDto> kafkaApartmentDtoTemplate(@Value("${kafka.bootstrapAddress}") String bootstrapAddress) {
        return new KafkaTemplate<>(producerFactoryApartmentDto(bootstrapAddress));
    }

    private ProducerFactory<String, ApartmentDto> producerFactoryApartmentDto(String bootstrapAddress) {
        Map<String, Object> properties = properties(bootstrapAddress);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(properties);
    }

    private Map<String, Object> properties(String bootstrapAddress) {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return properties;
    }
}
