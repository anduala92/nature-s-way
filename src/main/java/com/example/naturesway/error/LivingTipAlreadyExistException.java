package com.example.naturesway.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Duplicate living tip name!")
public class LivingTipAlreadyExistException extends BaseException{

    public LivingTipAlreadyExistException(String message) {
        super(HttpStatus.CONFLICT.value(), message);
    }

}
