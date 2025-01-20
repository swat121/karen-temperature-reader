package com.karen.karen_mqtt_integration.dto.device;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DeviceResponseDTO {
    private Long id;
    private String macAddress;
    private LocalDateTime createdAt;
}
