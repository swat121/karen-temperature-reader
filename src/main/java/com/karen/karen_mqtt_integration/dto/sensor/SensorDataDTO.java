package com.karen.karen_mqtt_integration.dto.sensor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SensorDataDTO {
    private Long id;
    private String address;
    private double value;
}
