package com.mediscreen.servicenotes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.OK)
public class NoteEmptyException extends RuntimeException {
    public NoteEmptyException(String message) {
        super(message);
    }
}
