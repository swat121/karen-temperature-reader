package com.karen.karen_mqtt_integration.service;

import com.karen.karen_mqtt_integration.entity.Device;
import com.karen.karen_mqtt_integration.entity.Request;
import com.karen.karen_mqtt_integration.entity.Sensor;
import com.karen.karen_mqtt_integration.repo.DeviceRepository;
import com.karen.karen_mqtt_integration.repo.RequestRepository;
import com.karen.karen_mqtt_integration.repo.SensorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RequestService {

    private final RequestRepository repository;
    private final DeviceRepository deviceRepository;
    private final SensorRepository sensorRepository;

    public Request addRequest(Long deviceId, Long sensorId, Request request) {
        Sensor sensor = sensorRepository.findById(sensorId).orElseThrow(() -> new IllegalArgumentException("Sensor not found"));
        Device device = deviceRepository.findById(deviceId).orElseThrow(() -> new IllegalArgumentException("Device not found"));

        request.setSensor(sensor);
        request.setDevice(device);

        return repository.save(request);
    }

    public Iterable<Request> findAllRequests() {
        return repository.findAll();
    }

    public void deleteAllRequests() {
        repository.deleteAll();
    }

    public long countRequests() {
        return repository.count();
    }

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    public Request findRequestById(Long id) {
        return repository.findById(id).orElse(null);
    }
}
