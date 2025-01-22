package com.karen.karen_mqtt_integration.dto.device;

import com.karen.karen_mqtt_integration.dto.sensor.SensorResponseDTO;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class DeviceDetailedResponseDTO {
    private Long id;
    private String macAddress;
    private LocalDateTime createdAt;
    private List<SensorResponseDTO> sensors;
}
