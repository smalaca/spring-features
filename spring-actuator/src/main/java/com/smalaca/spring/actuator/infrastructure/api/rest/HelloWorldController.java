package com.smalaca.spring.actuator.infrastructure.api.rest;

import io.micrometer.core.instrument.Counter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hello-world")
public class HelloWorldController {
    private final Counter helloCounter;

    HelloWorldController(Counter helloCounter) {
        this.helloCounter = helloCounter;
    }

    @GetMapping
    public String hello() {
        return "Hello world";
    }

    @GetMapping("/{name}")
    public String hello(@PathVariable String name) {
        helloCounter.increment();
        return "Hi " + name;
    }
}
