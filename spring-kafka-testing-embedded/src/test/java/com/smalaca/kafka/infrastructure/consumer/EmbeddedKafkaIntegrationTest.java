package com.smalaca.kafka.infrastructure.consumer;

import com.smalaca.kafka.infrastructure.producer.MessageProducer;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

import static java.time.Duration.ofSeconds;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(brokerProperties = { "listeners=PLAINTEXT://localhost:9010", "port=9010" })
class EmbeddedKafkaIntegrationTest {
    @Autowired
    private MessageConsumer consumer;

    @Autowired
    private MessageProducer producer;

    @Test
    void shouldConsumeMessage() {
        String payload = "Sending with own simple KafkaProducer";

        producer.send(payload);

        Awaitility.await()
                .atMost(ofSeconds(5))
                .untilAsserted(() -> assertThat(consumer.getPayload()).isEqualTo(payload));
    }
}