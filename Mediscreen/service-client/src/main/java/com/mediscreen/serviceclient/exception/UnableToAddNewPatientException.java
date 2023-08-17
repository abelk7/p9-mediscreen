package com.mediscreen.serviceclient.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class UnableToAddNewPatientException extends RuntimeException{
    public UnableToAddNewPatientException(String message) {
        super(message);
    }
}
