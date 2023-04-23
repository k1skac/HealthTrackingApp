package com.greenfoxacademy.hta.exceptions;

import org.springframework.http.HttpStatus;

public class ExerciseTimeNotFoundException extends HtaException{
    public ExerciseTimeNotFoundException() {
        super("Exercise time is not found", HttpStatus.NOT_FOUND);
    }
}
