package com.karen.karen_mqtt_integration.service;

import com.karen.karen_mqtt_integration.entity.Request;
import com.karen.karen_mqtt_integration.entity.SensorData;
import com.karen.karen_mqtt_integration.repo.RequestRepository;
import com.karen.karen_mqtt_integration.repo.SensorDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SensorDataService {

    private final SensorDataRepository repository;
    private final RequestRepository requestRepository;

    public void saveSensorData(Long requestId, SensorData data) {
        Request request = requestRepository.findById(requestId).orElseThrow(() -> new IllegalArgumentException("Request not found"));
        data.setRequest(request);

        repository.save(data);
    }

    public Iterable<SensorData> findAllSensorData() {
        return repository.findAll();
    }

    public void deleteAllSensorData() {
        repository.deleteAll();
    }

    public long countSensorData() {
        return repository.count();
    }

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    public SensorData findSensorDataById(Long id) {
        return repository.findById(id).orElse(null);
    }
}
