package com.karen.karen_mqtt_integration.repo;

import com.karen.karen_mqtt_integration.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeviceRepository extends JpaRepository<Device, Long> {

    Optional<Device> findByMacAddress(String macAddress);
}
