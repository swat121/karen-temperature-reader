package com.karen.karen_mqtt_integration.service;

import com.karen.karen_mqtt_integration.entity.Device;
import com.karen.karen_mqtt_integration.exception.ApiRequestException;
import com.karen.karen_mqtt_integration.exception.ErrorCode;
import com.karen.karen_mqtt_integration.repo.DeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeviceService {

    private final DeviceRepository repository;

    public Device saveDevice(Device device) {
        return repository.save(device);
    }

    public Device findByMacAddress(String macAddress) {
        return repository.findByMacAddress(macAddress).orElseThrow(() -> new ApiRequestException(ErrorCode.ENTITY_NOT_FOUND, "Device with macAddress: " + macAddress + " not found"));
    }

    public Device updateDevice(Long id, Device device) {
        Device existingDevice = repository.findById(id)
                .orElseThrow(() -> new ApiRequestException(
                        ErrorCode.ENTITY_NOT_FOUND,
                        "Device with id: " + id + " not found"
                ));

        existingDevice.setMacAddress(device.getMacAddress());
        existingDevice.setCreatedAt(device.getCreatedAt());

        return repository.save(existingDevice);
    }

    public Iterable<Device> findAllDevices() {
        return repository.findAll();
    }

    public Device findDeviceById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ApiRequestException(ErrorCode.ENTITY_NOT_FOUND, "Device with id: " + id + " not found"));
    }

    public void deleteDeviceById(Long id) {
        Device existingDevice = repository.findById(id)
                .orElseThrow(() -> new ApiRequestException(
                        ErrorCode.ENTITY_NOT_FOUND,
                        "Device with id: " + id + " not found"
                ));

        repository.delete(existingDevice);
    }

    public void deleteAllDevices() {
        repository.deleteAll();
    }

    public long countDevices() {
        return repository.count();
    }

    public boolean existByMacAddress(String macAddress) {
        return repository.existsByMacAddress(macAddress);
    }

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

}
