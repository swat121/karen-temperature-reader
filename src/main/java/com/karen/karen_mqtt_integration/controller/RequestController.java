package com.karen.karen_mqtt_integration.controller;

import com.karen.karen_mqtt_integration.dto.sensor.SensorDataResponseDTO;
import com.karen.karen_mqtt_integration.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/requests")
@RequiredArgsConstructor
public class RequestController {

    private final RequestService requestService;

    @GetMapping("/sensor-data")
    public ResponseEntity<List<SensorDataResponseDTO>> getSensorData(
            @RequestParam String macAddress,
            @RequestParam String sensorName) {
        List<SensorDataResponseDTO> sensorData = requestService.getSensorData(macAddress, sensorName);
        return ResponseEntity.ok(sensorData);
    }
}
