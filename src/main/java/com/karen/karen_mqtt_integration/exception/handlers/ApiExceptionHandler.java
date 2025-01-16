package com.karen.karen_mqtt_integration.exception.handlers;

import com.karen.karen_mqtt_integration.exception.ApiRequestException;
import com.karen.karen_mqtt_integration.exception.ErrorCode;
import com.karen.karen_mqtt_integration.exception.ErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZonedDateTime;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(ApiRequestException.class)
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException e) {
        ErrorCode errorCode = e.getErrorCode();
        return new ResponseEntity<>(buildErrorResponse("ApiRequestException: " + e.getMessage(), errorCode), errorCode.getHttpStatus());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResponse handleBadRequestException(IllegalArgumentException ex) {
        return buildErrorResponse("IllegalArgumentException: " + ex.getMessage(), ErrorCode.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ErrorResponse handleDeviceNotFoundException(EntityNotFoundException ex) {
        return buildErrorResponse("EntityNotFoundException: " + ex.getMessage(), ErrorCode.ENTITY_NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleUnexpectedException(Exception e) {
        System.out.println("Unexpected exception occurred: " + e);

        return buildErrorResponse("Unexpected error occurred. Please contact support.", ErrorCode.SERVER_ERROR);
    }

    private ErrorResponse buildErrorResponse(String message, ErrorCode errorCode) {
        return new ErrorResponse(
                message,
                ZonedDateTime.now(),
                errorCode
        );
    }
}
