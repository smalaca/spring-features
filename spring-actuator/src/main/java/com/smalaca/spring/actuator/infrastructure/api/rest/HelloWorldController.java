package com.smalaca.spring.actuator.infrastructure.api.rest;

import com.smalaca.spring.actuator.datamodel.history.History;
import com.smalaca.spring.actuator.datamodel.history.HistoryRepository;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Timer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("hello-world")
public class HelloWorldController {
    private final HistoryRepository historyRepository;
    private final Counter helloCounter;
    private final Timer historyTime;

    HelloWorldController(
            HistoryRepository historyRepository, Counter helloCounter, Timer historyTime) {
        this.historyRepository = historyRepository;
        this.helloCounter = helloCounter;
        this.historyTime = historyTime;
    }

    @GetMapping
    public String hello() {
        return historyTime.record(() -> {
            historyRepository.save(new History(UUID.randomUUID().toString()));
            return "Hello world";
        });
    }

    @GetMapping("/{name}")
    public String hello(@PathVariable String name) {
        helloCounter.increment();
        return "Hi " + name;
    }
}
