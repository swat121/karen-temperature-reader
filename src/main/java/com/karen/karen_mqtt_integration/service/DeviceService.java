package com.karen.karen_mqtt_integration.service;

import com.karen.karen_mqtt_integration.dto.device.DeviceDetailedResponseDTO;
import com.karen.karen_mqtt_integration.dto.device.DeviceRequestDTO;
import com.karen.karen_mqtt_integration.dto.device.DeviceResponseDTO;
import com.karen.karen_mqtt_integration.dto.device.DeviceUpdateDTO;
import com.karen.karen_mqtt_integration.dto.sensor.SensorResponseDTO;
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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeviceService {

    private final ModelMapper modelMapper;
    private final DeviceRepository repository;

    private final Type listType = new TypeToken<List<DeviceResponseDTO>>() {
    }.getType();

    public DeviceResponseDTO saveDevice(DeviceRequestDTO device) {
        return modelMapper.map(repository.save(Device
                .builder()
                .macAddress(device.getMacAddress())
                .build()), DeviceResponseDTO.class);
    }

    public DeviceResponseDTO findByMacAddress(String macAddress) {
        Device device = repository.findByMacAddress(macAddress)
                .orElseThrow(() -> new ApiRequestException(
                        ErrorCode.ENTITY_NOT_FOUND,
                        "Device with macAddress: " + macAddress + " not found"
                ));
        return modelMapper.map(device, DeviceResponseDTO.class);
    }

    public Device findAndSaveDevice(String macAddress) {
        Optional<Device> device = repository.findByMacAddress(macAddress);
        return device.orElseGet(() -> repository.save(Device
                .builder()
                .macAddress(macAddress)
                .build()));
    }

    public DeviceUpdateDTO updateDevice(Long id, DeviceRequestDTO device) {
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

    public boolean existsByMacAddress(String macAddress) {
        return repository.existsByMacAddress(macAddress);
    }

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    public DeviceDetailedResponseDTO getDeviceWithSensors(Long id) {
        Device device = repository.findById(id)
                .orElseThrow(() -> new ApiRequestException(ErrorCode.ENTITY_NOT_FOUND, "Device not found"));

        return modelMapper.map(device, DeviceDetailedResponseDTO.class);
    }
}
