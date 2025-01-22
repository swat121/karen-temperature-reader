package com.karen.karen_mqtt_integration.repo;

import com.karen.karen_mqtt_integration.entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SensorRepository extends JpaRepository<Sensor, Long> {
    Optional<Sensor> findByName(String name);

    boolean existsByName(String name);
}
