package com.karen.karen_mqtt_integration.repo;

import com.karen.karen_mqtt_integration.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<Request, Long> {
}
