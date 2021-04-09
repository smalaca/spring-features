package com.smalaca.spring.kafka.configuration;

import com.smalaca.spring.kafka.dto.UserDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfiguration {
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, UserDto> userDtoKafkaListenerContainerFactory(
            @Value("${spring.kafka.bootstrap-servers}") String bootstrapAddress) {
        ConcurrentKafkaListenerContainerFactory<String, UserDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        DefaultKafkaConsumerFactory<String, UserDto> consumerFactory = new DefaultKafkaConsumerFactory<>(
                propertiesFor(bootstrapAddress), new StringDeserializer(), new JsonDeserializer<>(UserDto.class));
        factory.setConsumerFactory(consumerFactory);

        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory(
            @Value("${spring.kafka.bootstrap-servers}") String bootstrapAddress, KafkaTemplate<String, String> kafkaTemplate) {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        DefaultKafkaConsumerFactory<String, String> consumerFactory = new DefaultKafkaConsumerFactory<>(
                propertiesFor(bootstrapAddress), new StringDeserializer(), new StringDeserializer());
        factory.setConsumerFactory(consumerFactory);
        factory.setReplyTemplate(kafkaTemplate);

        return factory;
    }

    private Map<String, Object> propertiesFor(String bootstrapAddress) {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "group-one");

        return properties;
    }
}
