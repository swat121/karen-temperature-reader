package com.karen.karen_mqtt_integration.service;

import com.karen.karen_mqtt_integration.dto.sensor.SensorRequestDTO;
import com.karen.karen_mqtt_integration.dto.sensor.SensorResponseDTO;
import com.karen.karen_mqtt_integration.entity.Device;
import com.karen.karen_mqtt_integration.entity.Sensor;
import com.karen.karen_mqtt_integration.exception.ApiRequestException;
import com.karen.karen_mqtt_integration.exception.ErrorCode;
import com.karen.karen_mqtt_integration.repo.DeviceRepository;
import com.karen.karen_mqtt_integration.repo.SensorRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.internal.bytebuddy.description.method.MethodDescription;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SensorService {

    private final SensorRepository repository;
    private final DeviceRepository deviceRepository;
    private final ModelMapper modelMapper;
    private final Type listType = new TypeToken<List<SensorResponseDTO>>() {
    }.getType();

    public SensorResponseDTO saveSensor(Long deviceId, SensorRequestDTO sensor) {
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new IllegalArgumentException("Device with id: " + deviceId + "not found"));

        return modelMapper.map(repository.save(Sensor
                .builder()
                .name(sensor.getName())
                .device(device)
                .build()), SensorResponseDTO.class);
    }

    public SensorResponseDTO findByName(String name) {
        return modelMapper.map(repository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Sensor with name: " + name + " not found")), SensorResponseDTO.class);
    }

    public Sensor findAndSaveSensor(Device device, String name) {
        Optional<Sensor> sensor = repository.findByName(name);
        return sensor.orElseGet(() -> repository.save(Sensor
                .builder()
                .name(name)
                .device(device)
                .build()));
    }

    public Iterable<SensorResponseDTO> findAllSensors() {
        return modelMapper.map(repository.findAll(), listType);
    }

    public SensorResponseDTO findSensorById(Long id) {
        return modelMapper.map(repository.findById(id)
                .orElseThrow(() -> new ApiRequestException(ErrorCode.ENTITY_NOT_FOUND, "Sensor with id: " + id + " not found")), SensorResponseDTO.class);
    }

    public boolean existsByName(String name) {
        return repository.existsByName(name);
    }

    public void deleteSensorById(Long id) {
        repository.deleteById(id);
    }

    public void deleteAllSensors() {
        repository.deleteAll();
    }
}
