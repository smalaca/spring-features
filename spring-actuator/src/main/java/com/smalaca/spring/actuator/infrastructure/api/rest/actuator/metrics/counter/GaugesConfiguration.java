package com.smalaca.spring.actuator.infrastructure.api.rest.actuator.metrics.counter;

import com.smalaca.spring.actuator.datamodel.history.HistoryRepository;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GaugesConfiguration {
    @Bean
    public Gauge countProfilesGauge(MeterRegistry registry, HistoryRepository repository) {
        return Gauge
                .builder("api.history.count", repository::count)
                .description("Amount of events in history")
                .register(registry);
    }
}
