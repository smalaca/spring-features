package com.smalaca.spring.kafka.producer;

import com.smalaca.spring.kafka.dto.UserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.RoutingKafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.stream.IntStream;

@Component
class OnStartupProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private KafkaTemplate<String, String> kafkaTemplateWithProducerListener;
    private RoutingKafkaTemplate routingKafkaTemplate;
    private final KafkaTopics kafkaTopics;
    private final String topicWithOwnHeader;

    OnStartupProducer(
            KafkaTemplate<String, String> kafkaTemplate, KafkaTemplate<String, String> kafkaTemplateWithProducerListener,
            RoutingKafkaTemplate routingKafkaTemplate,
            KafkaTopics kafkaTopics, @Value("${topics.with-header.topic-five}") String topicWithOwnHeader) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaTemplateWithProducerListener = kafkaTemplateWithProducerListener;
        this.routingKafkaTemplate = routingKafkaTemplate;
        this.kafkaTopics = kafkaTopics;
        this.topicWithOwnHeader = topicWithOwnHeader;
    }

    @Async
    @EventListener
    public void listenerOne(ContextRefreshedEvent event) {
        IntStream.range(0, 10).forEach(index -> {
            kafkaTopics.forEach(topicName -> {
                String message = message(index, topicName);
                ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicName, message);

                future.addCallback(new ListenableFutureCallback<>() {
                    @Override
                    public void onFailure(Throwable exception) {
                        System.out.println("EXCEPTION THROWN: " + message);
                    }

                    @Override
                    public void onSuccess(SendResult<String, String> result) {
                        System.out.println("SUCCESS: " + message);
                    }
                });
            });
        });
    }

    @Async
    @EventListener
    public void listenerTwo(ContextRefreshedEvent event) {
        IntStream.range(0, 10).forEach(index -> {
            Message<String> message = MessageBuilder.withPayload("With HEADER" + message(index, topicWithOwnHeader))
                    .setHeader(KafkaHeaders.TOPIC, topicWithOwnHeader)
                    .setHeader("my-header", "header value " + index)
                    .build();
            kafkaTemplateWithProducerListener.send(message);
        });
    }

    @Async
    @EventListener
    public void listenerThree(ContextRefreshedEvent event) {
        IntStream.range(0, 10).forEach(index -> {
            routingKafkaTemplate.send(kafkaTopics.routingString(), message(index, kafkaTopics.routingString()));
            routingKafkaTemplate.send(kafkaTopics.routingUser(), new UserDto("smalaca" + index));
        });
    }

    private String message(int index, String topicName) {
        return "Topic: " + topicName + ", Message: This is crazy " + index;
    }
}
