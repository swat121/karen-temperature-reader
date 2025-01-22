package com.karen.karen_mqtt_integration.dto.sensor;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SensorRequestDTO {
    private String name;
}
