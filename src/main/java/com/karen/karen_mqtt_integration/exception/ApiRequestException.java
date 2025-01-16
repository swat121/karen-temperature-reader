package com.karen.karen_mqtt_integration.exception;

import lombok.Getter;

@Getter
public class ApiRequestException extends RuntimeException{
    private final ErrorCode errorCode;

    public ApiRequestException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ApiRequestException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

}
