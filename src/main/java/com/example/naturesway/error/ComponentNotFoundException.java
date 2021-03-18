package com.example.naturesway.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid bicycle component id")
public class ComponentNotFoundException extends BaseException {

    public ComponentNotFoundException(String message) {
        super(HttpStatus.BAD_REQUEST.value(), message);
    }
}
