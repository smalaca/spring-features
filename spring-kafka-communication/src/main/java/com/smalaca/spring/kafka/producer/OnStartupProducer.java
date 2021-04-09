package com.smalaca.spring.kafka.producer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
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
    private final KafkaTemplate<String, String> template;
    private final KafkaTopics kafkaTopics;
    private final String topicWithOwnHeader;

    OnStartupProducer(
            KafkaTemplate<String, String> template, KafkaTopics kafkaTopics,
            @Value("${topics.with-header.topic-five}") String topicWithOwnHeader) {
        this.template = template;
        this.kafkaTopics = kafkaTopics;
        this.topicWithOwnHeader = topicWithOwnHeader;
    }

    @Async
    @EventListener
    public void listenerOne(ContextRefreshedEvent event) {
        IntStream.range(0, 10).forEach(index -> {
            kafkaTopics.forEach(topicName -> {
                String message = message(index, topicName);
                ListenableFuture<SendResult<String, String>> future = template.send(topicName, message);

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
            template.send(message);
        });
    }

    private String message(int index, String topicName) {
        return "Topic: " + topicName + ", Message: This is crazy " + index;
    }
}
