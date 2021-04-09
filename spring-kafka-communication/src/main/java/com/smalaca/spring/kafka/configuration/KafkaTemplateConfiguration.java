package com.smalaca.spring.kafka.configuration;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.ProducerListener;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTemplateConfiguration {
    @Bean
    public KafkaTemplate<String, String> kafkaTemplateWithProducerListener(@Value("${spring.kafka.bootstrap-servers}") String bootstrapServers) {
        KafkaTemplate<String, String> kafkaTemplate = new KafkaTemplate<>(producerFactory(bootstrapServers));
        kafkaTemplate.setProducerListener(new ProducerListener<>() {
            @Override
            public void onSuccess(ProducerRecord<String, String> producerRecord, RecordMetadata recordMetadata) {
                System.out.println("SUCCESS in ProducerListener for: " + producerRecord + " and " + recordMetadata);
            }
        });
        return kafkaTemplate;
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate(@Value("${spring.kafka.bootstrap-servers}") String bootstrapServers) {
        return new KafkaTemplate<>(producerFactory(bootstrapServers));
    }

    private ProducerFactory<String, String> producerFactory(String bootstrapServers) {
        Map<String, Object> properties = properties(bootstrapServers);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return new DefaultKafkaProducerFactory<>(properties);
    }


    private Map<String, Object> properties(String bootstrapServers) {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return properties;
    }
}
