package com.greenfoxacademy.hta.exceptions;

import org.springframework.http.HttpStatus;

public class WeightNotFoundException extends HtaException{
    public WeightNotFoundException(String message, HttpStatus status) {
        super(message, status);
    }

    public WeightNotFoundException(String message) {
        super(message);
    }
}
