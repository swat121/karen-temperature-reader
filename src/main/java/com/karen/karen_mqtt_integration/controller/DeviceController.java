package com.karen.karen_mqtt_integration.controller;

import com.karen.karen_mqtt_integration.entity.Device;
import com.karen.karen_mqtt_integration.service.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/devices")
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceService deviceService;

    @GetMapping("/{id}")
    public ResponseEntity<Device> getDeviceById(@PathVariable Long id) {
        Device device = deviceService.findDeviceById(id);
        if (device != null) {
            return new ResponseEntity<>(device, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<Iterable<Device>> getAllDevices() {
        return ResponseEntity.ok(deviceService.findAllDevices());
    }

    @PostMapping
    public ResponseEntity<Device> createDevice(@RequestBody Device device) {
        Device createdDevice = deviceService.saveDevice(device);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDevice);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Device> updateDevice(@PathVariable Long id, @RequestBody Device device) {
        if (!deviceService.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        device.setId(id);
        Device updatedDevice = deviceService.saveDevice(device);
        return ResponseEntity.ok(updatedDevice);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeviceById(@PathVariable Long id) {
        if (!deviceService.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        deviceService.deleteDeviceById(id);
        return ResponseEntity.noContent().build(); // HTTP 204
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
}
