package com.karen.karen_mqtt_integration.service;

import com.karen.karen_mqtt_integration.dto.device.DeviceRequestDTO;
import com.karen.karen_mqtt_integration.dto.device.DeviceResponseDTO;
import com.karen.karen_mqtt_integration.dto.device.DeviceUpdateDTO;
import com.karen.karen_mqtt_integration.entity.Device;
import com.karen.karen_mqtt_integration.exception.ApiRequestException;
import com.karen.karen_mqtt_integration.exception.ErrorCode;
import com.karen.karen_mqtt_integration.repo.DeviceRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeviceService {

    private final ModelMapper modelMapper;
    private final DeviceRepository repository;

    private final Type listType = new TypeToken<List<DeviceResponseDTO>>() {
    }.getType();

    public DeviceRequestDTO saveDevice(Device device) {
        return modelMapper.map(repository.save(device), DeviceRequestDTO.class);
    }

    public DeviceRequestDTO findByMacAddress(String macAddress) {
        return modelMapper.map(repository.findByMacAddress(macAddress)
                .orElseThrow(() -> new ApiRequestException(ErrorCode.ENTITY_NOT_FOUND, "Device with macAddress: " + macAddress + " not found")), DeviceRequestDTO.class);
    }

    public DeviceUpdateDTO updateDevice(Long id, Device device) {
        Device existingDevice = repository.findById(id)
                .orElseThrow(() -> new ApiRequestException(
                        ErrorCode.ENTITY_NOT_FOUND,
                        "Device with id: " + id + " not found"
                ));

        existingDevice.setMacAddress(device.getMacAddress());
        existingDevice.setCreatedAt(device.getCreatedAt());

        return modelMapper.map(repository.save(existingDevice), DeviceUpdateDTO.class);
    }

    public Iterable<DeviceResponseDTO> findAllDevices() {
        return modelMapper.map(repository.findAll(), listType);
    }

    public DeviceResponseDTO findDeviceById(Long id) {

        return modelMapper.map(repository.findById(id)
                .orElseThrow(() -> new ApiRequestException(ErrorCode.ENTITY_NOT_FOUND, "Device with id: " + id + " not found")), DeviceResponseDTO.class);
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
