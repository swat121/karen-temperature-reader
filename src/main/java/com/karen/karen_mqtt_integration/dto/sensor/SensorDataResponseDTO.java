package com.karen.karen_mqtt_integration.dto.sensor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SensorDataResponseDTO {
    private String requestId;
    private String macAddress;
    private String sensorName;
    private List<SensorDataDTO> sensorData;
    private ZonedDateTime receivedAt;
}
