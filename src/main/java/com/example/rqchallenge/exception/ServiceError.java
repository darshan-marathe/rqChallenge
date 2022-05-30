package com.example.rqchallenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.SERVICE_UNAVAILABLE)
public class ServiceError extends RuntimeException {
    public ServiceError(String message) {
        super(message);
    }
}
