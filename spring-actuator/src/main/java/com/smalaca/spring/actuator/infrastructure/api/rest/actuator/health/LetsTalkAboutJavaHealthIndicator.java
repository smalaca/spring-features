package com.smalaca.spring.actuator.infrastructure.api.rest.actuator.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class LetsTalkAboutJavaHealthIndicator implements HealthIndicator {
    public static final String LTAJ_BLOG = "http://letstalkaboutjava.blogspot.com/";
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public Health health() {
        try {
            ResponseEntity<String> response = response();

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return Health.up().build();
            } else {
                return Health.down().withDetail("status", response.getStatusCode()).build();
            }
        } catch (RestClientException ex) {
            return Health.down().withException(ex).build();
        }
    }

    private ResponseEntity<String> response() {
        return restTemplate.exchange(LTAJ_BLOG, HttpMethod.GET, null, String.class);
    }
}
