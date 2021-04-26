package com.smalaca.spring.actuator.infrastructure.api.rest.actuator.metrics.counter;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class TimersConfiguration {
    @Bean
    public Timer historyTime(MeterRegistry registry) {
        return Timer
                .builder("api.history.time")
                .description("Time taken to create event in history")
                .sla(Duration.ofMillis(10))
                .register(registry);
    }
}
