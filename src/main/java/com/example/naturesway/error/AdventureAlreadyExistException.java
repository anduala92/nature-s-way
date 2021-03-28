package com.example.naturesway.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Duplicate adventure name!")
public class AdventureAlreadyExistException extends BaseException{

    public AdventureAlreadyExistException(String message) {
        super(HttpStatus.CONFLICT.value(), message);
    }

}
