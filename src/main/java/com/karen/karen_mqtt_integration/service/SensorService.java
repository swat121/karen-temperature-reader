package com.karen.karen_mqtt_integration.service;

import com.karen.karen_mqtt_integration.entity.Device;
import com.karen.karen_mqtt_integration.entity.Sensor;
import com.karen.karen_mqtt_integration.repo.DeviceRepository;
import com.karen.karen_mqtt_integration.repo.SensorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SensorService {

    private final SensorRepository repository;
    private final DeviceRepository deviceRepository;

    public Sensor saveSensor(Long deviceId, Sensor sensor) {
        Device device = deviceRepository.findById(deviceId).orElseThrow(() -> new IllegalArgumentException("Device not found"));

        sensor.setDevice(device);
        return repository.save(sensor);
    }

    public Optional<Sensor> findByName(String name) {
        return repository.findByName(name);
    }

    public Iterable<Sensor> findAllSensors() {
        return repository.findAll();
    }

    public Sensor findSensorById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void deleteSensorById(Long id) {
        repository.deleteById(id);
    }

    public void deleteAllSensors() {
        repository.deleteAll();
    }
}
