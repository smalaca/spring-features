package com.smalaca.spring.actuator.infrastructure.api.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hello-world")
public class HelloWorldController {
    @GetMapping
    public String hello() {
        return "Hello world";
    }
}
