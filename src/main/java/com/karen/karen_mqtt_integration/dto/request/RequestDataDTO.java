package com.karen.karen_mqtt_integration.dto.request;

import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@Builder
public class RequestDataDTO {
    private Long id;
    private String requestId;
    private ZonedDateTime receivedAt;
}
