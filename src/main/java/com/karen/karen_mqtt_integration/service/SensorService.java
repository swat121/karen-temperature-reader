package com.karen.karen_mqtt_integration.service;

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

    public SensorResponseDTO saveSensor(Long deviceId, Sensor sensor) {
        Device device = deviceRepository.findById(deviceId).orElseThrow(() -> new IllegalArgumentException("Device not found"));

        sensor.setDevice(device);
        return modelMapper.map(repository.save(sensor), SensorResponseDTO.class);
    }

    public SensorResponseDTO findByName(String name) {
        return modelMapper.map(repository.findByName(name), SensorResponseDTO.class);
    }

    public Iterable<SensorResponseDTO> findAllSensors() {
        return modelMapper.map(repository.findAll(), listType);
    }

    public SensorResponseDTO findSensorById(Long id) {
        return modelMapper.map(repository.findById(id)
                .orElseThrow(() -> new ApiRequestException(ErrorCode.ENTITY_NOT_FOUND, "Sensor with id: " + id + " not found")), SensorResponseDTO.class);
    }

    public void deleteSensorById(Long id) {
        repository.deleteById(id);
    }

    public void deleteAllSensors() {
        repository.deleteAll();
    }
}
