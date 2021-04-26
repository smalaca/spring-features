package com.smalaca.spring.actuator.datamodel.history;

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

    public History(String name) {
        this.name = name;
    }
}
