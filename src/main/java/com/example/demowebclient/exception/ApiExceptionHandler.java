package com.example.demowebclient.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;


public class ApiExceptionHandler extends RuntimeException {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private final LocalDateTime timestamp;
    private final String message;

    public ApiExceptionHandler(String message) {
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    @Override
    public String getMessage() {
        return message;
    }
}

