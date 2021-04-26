package com.smalaca.spring.actuator.datamodel.history;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface HistoryRepository extends CrudRepository<History, UUID> {
}
