package com.karen.karen_mqtt_integration.repo;

import com.karen.karen_mqtt_integration.entity.SensorData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorDataRepository extends JpaRepository<SensorData, Long> {
}
