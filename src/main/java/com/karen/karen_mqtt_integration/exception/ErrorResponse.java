package com.karen.karen_mqtt_integration.exception;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class ErrorResponse {
    private final String message;
    private final ErrorCode errorCode;
    private final ZonedDateTime time;

    @JsonCreator
    public ErrorResponse(@JsonProperty("message") String message,
                         @JsonProperty("time") ZonedDateTime time,
                         @JsonProperty("errorCode") ErrorCode errorCode) {
        this.message = message;
        this.time = time;
        this.errorCode = errorCode;
    }
}
