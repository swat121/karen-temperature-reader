package com.karen.karen_mqtt_integration.dto;

import lombok.Data;

import java.util.List;

@Data
public class MQTTMessageDTO {
    private String macAddress;
    private String requestId;
    private String sensor;

    private List<SensorDataDTO> data;

    @Data
    public static class SensorDataDTO {
        private String address;
        private double value;
    }
}
