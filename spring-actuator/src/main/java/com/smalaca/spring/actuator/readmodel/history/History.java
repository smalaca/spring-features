package com.smalaca.spring.actuator.readmodel.history;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class History {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    private History() {}

    private History(String name) {
        this.name = name;
    }
}
