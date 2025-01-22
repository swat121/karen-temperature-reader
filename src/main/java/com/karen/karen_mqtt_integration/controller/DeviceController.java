package com.karen.karen_mqtt_integration.controller;

import com.karen.karen_mqtt_integration.dto.device.DeviceDetailedResponseDTO;
import com.karen.karen_mqtt_integration.dto.device.DeviceRequestDTO;
import com.karen.karen_mqtt_integration.dto.device.DeviceResponseDTO;
import com.karen.karen_mqtt_integration.dto.device.DeviceUpdateDTO;
import com.karen.karen_mqtt_integration.entity.Device;
import com.karen.karen_mqtt_integration.service.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/devices")
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceService deviceService;

    @GetMapping("/{id}")
    public ResponseEntity<DeviceResponseDTO> getDeviceById(@PathVariable Long id) {
         return ResponseEntity.ok(deviceService.findDeviceById(id));
    }

    @GetMapping
    public ResponseEntity<Iterable<DeviceResponseDTO>> getAllDevices() {
        return ResponseEntity.ok(deviceService.findAllDevices());
    }

    @GetMapping("/{macAddress}")
    public ResponseEntity<DeviceResponseDTO> getDeviceByMacAddress(@PathVariable String macAddress) {
        return ResponseEntity.ok(deviceService.findByMacAddress(macAddress));
    }

    @PostMapping
    public ResponseEntity<DeviceResponseDTO> createDevice(@RequestBody DeviceRequestDTO device) {
        DeviceResponseDTO createdDevice = deviceService.saveDevice(device);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDevice);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeviceUpdateDTO> updateDevice(@PathVariable Long id, @RequestBody DeviceRequestDTO device) {
        DeviceUpdateDTO updatedDevice = deviceService.updateDevice(id, device);
        return ResponseEntity.ok(updatedDevice);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeviceById(@PathVariable Long id) {
        deviceService.deleteDeviceById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllDevices() {
        deviceService.deleteAllDevices();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countDevices() {
        return ResponseEntity.ok(deviceService.countDevices());
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<DeviceDetailedResponseDTO> getDeviceWithSensors(@PathVariable Long id) {
        return ResponseEntity.ok(deviceService.getDeviceWithSensors(id));
    }
}
