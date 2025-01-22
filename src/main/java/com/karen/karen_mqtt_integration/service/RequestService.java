package com.karen.karen_mqtt_integration.service;

import com.karen.karen_mqtt_integration.dto.request.RequestDataDTO;
import com.karen.karen_mqtt_integration.dto.sensor.SensorDataDTO;
import com.karen.karen_mqtt_integration.dto.sensor.SensorDataResponseDTO;
import com.karen.karen_mqtt_integration.entity.Device;
import com.karen.karen_mqtt_integration.entity.Request;
import com.karen.karen_mqtt_integration.entity.Sensor;
import com.karen.karen_mqtt_integration.exception.ApiRequestException;
import com.karen.karen_mqtt_integration.exception.ErrorCode;
import com.karen.karen_mqtt_integration.repo.DeviceRepository;
import com.karen.karen_mqtt_integration.repo.RequestRepository;
import com.karen.karen_mqtt_integration.repo.SensorRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestService {

    private final RequestRepository repository;
    private final DeviceRepository deviceRepository;
    private final SensorRepository sensorRepository;
    private final ModelMapper modelMapper;
    private final Type listType = new TypeToken<List<RequestDataDTO>>() {
    }.getType();

    public Request saveRequest(Long deviceId, Long sensorId, Request request) {
        Sensor sensor = sensorRepository.findById(sensorId).orElseThrow(() -> new IllegalArgumentException("Sensor not found"));
        Device device = deviceRepository.findById(deviceId).orElseThrow(() -> new IllegalArgumentException("Device not found"));

        request.setSensor(sensor);
        request.setDevice(device);

        return repository.save(request);
    }

    public Iterable<RequestDataDTO> findAllRequests() {
        return modelMapper.map(repository.findAll(), listType);
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

    public RequestDataDTO findRequestById(Long id) {
        return modelMapper.map(repository.findById(id)
                .orElseThrow(() -> new ApiRequestException(ErrorCode.ENTITY_NOT_FOUND, "Request with id: " + id + " not found")), RequestDataDTO.class);
    }

    public List<SensorDataResponseDTO> getSensorData(String macAddress, String sensorName) {
        List<Request> requests = repository.findByDeviceMacAddressAndSensorName(macAddress, sensorName);

        return requests.stream()
                .map(request -> new SensorDataResponseDTO(
                        request.getDevice().getMacAddress(),
                        request.getSensor().getName(),
                        request.getSensorData().stream()
                                .map(sensorData -> new SensorDataDTO(
                                        sensorData.getId(),
                                        sensorData.getAddress(),
                                        sensorData.getValue()
                                ))
                                .toList(),
                        request.getReceivedAt()
                ))
                .toList();
    }
}
