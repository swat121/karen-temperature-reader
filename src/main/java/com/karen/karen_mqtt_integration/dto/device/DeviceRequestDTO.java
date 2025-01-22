package com.karen.karen_mqtt_integration.dto.device;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class DeviceRequestDTO {
    private String macAddress;
    private LocalDateTime createdAt;
}