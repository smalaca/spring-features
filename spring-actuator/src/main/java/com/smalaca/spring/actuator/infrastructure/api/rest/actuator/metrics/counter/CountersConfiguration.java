package com.smalaca.spring.actuator.infrastructure.api.rest.actuator.metrics.counter;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CountersConfiguration {
    @Bean
    public Counter helloCounter(MeterRegistry registry) {
        return Counter
                .builder("api.rest.hello-counter")
                .description("Amount of times hello was said")
                .register(registry);
    }
}
