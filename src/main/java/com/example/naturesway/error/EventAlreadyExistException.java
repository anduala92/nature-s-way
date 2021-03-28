package com.example.naturesway.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Duplicate event name!")
public class EventAlreadyExistException extends BaseException{

    public EventAlreadyExistException(String message) {
        super(HttpStatus.CONFLICT.value(), message);
    }

}
