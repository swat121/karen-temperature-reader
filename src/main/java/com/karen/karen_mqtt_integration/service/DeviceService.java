package com.karen.karen_mqtt_integration.service;

import com.karen.karen_mqtt_integration.entity.Device;
import com.karen.karen_mqtt_integration.repo.DeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeviceService {

    private final DeviceRepository repository;

    public Device saveDevice(Device device) {
        return repository.save(device);
    }

    public Optional<Device> findByMacAddress(String macAddress) {
        return repository.findByMacAddress(macAddress);
    }

    public Iterable<Device> findAllDevices() {
        return repository.findAll();
    }

    public Device findDeviceById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void deleteDeviceById(Long id) {
        repository.deleteById(id);
    }

    public void deleteAllDevices() {
        repository.deleteAll();
    }

    public long countDevices() {
        return repository.count();
    }

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

}
