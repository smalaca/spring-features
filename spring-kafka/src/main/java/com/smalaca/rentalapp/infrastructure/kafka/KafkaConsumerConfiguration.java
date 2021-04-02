package com.smalaca.rentalapp.infrastructure.kafka;

import com.smalaca.rentalapp.infrastructure.producer.ApartmentDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfiguration {
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> stringKafkaListenerContainerFactory(
            @Value("${kafka.bootstrapAddress}") String bootstrapAddress) {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory(bootstrapAddress));

        return factory;
    }

    private ConsumerFactory<String, String> consumerFactory(String bootstrapAddress) {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        return new DefaultKafkaConsumerFactory<>(properties);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ApartmentDto> apartmentDtoEvenKafkaListenerContainerFactory(
            @Value("${kafka.bootstrapAddress}") String bootstrapAddress) {
        ConcurrentKafkaListenerContainerFactory<String, ApartmentDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryApartmentDto(bootstrapAddress));
        factory.setRecordFilterStrategy(record -> record.value().getNumber() % 2 != 0);

        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ApartmentDto> apartmentDtoOddKafkaListenerContainerFactory(
            @Value("${kafka.bootstrapAddress}") String bootstrapAddress) {
        ConcurrentKafkaListenerContainerFactory<String, ApartmentDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryApartmentDto(bootstrapAddress));
        factory.setRecordFilterStrategy(record -> record.value().getNumber() % 2 == 0);

        return factory;
    }

    private ConsumerFactory<String, ApartmentDto> consumerFactoryApartmentDto(String bootstrapAddress) {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        properties.put(JsonDeserializer.TRUSTED_PACKAGES, "com.smalaca.rentalapp.infrastructure.producer");

        return new DefaultKafkaConsumerFactory<>(properties);
    }
}
