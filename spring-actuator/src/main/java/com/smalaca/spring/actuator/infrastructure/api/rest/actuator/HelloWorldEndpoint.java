package com.smalaca.spring.actuator.infrastructure.api.rest.actuator;

import org.springframework.boot.actuate.endpoint.annotation.*;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Endpoint(id = "hello-world")
public class HelloWorldEndpoint {
    private final Map<String, Object> properties = new HashMap<>();

    @ReadOperation
    public String sayHelloWorld() {
        return "Hello everyone in actuator world. Properties: " + properties;
    }

    @ReadOperation
    public String sayHello(@Selector String firstName, @Selector String lastName) {
        return "Hello " + firstName + " " + lastName;
    }

    @WriteOperation
    public void addProperty(String name, Object value) {
        properties.put(name, value);
    }

    @DeleteOperation
    public void removeProperty(@Selector String name) {
        properties.remove(name);
    }
}
