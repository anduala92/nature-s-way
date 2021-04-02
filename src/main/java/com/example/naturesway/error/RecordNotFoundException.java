package com.example.naturesway.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid Adventure id")
public class RecordNotFoundException extends BaseException {

    public RecordNotFoundException(String message) {
        super(HttpStatus.BAD_REQUEST.value(), message);
    }
}
