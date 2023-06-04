package com.greenfoxacademy.hta.exceptions;

import org.springframework.http.HttpStatus;

public class MealDoesNotExistException extends HtaException {
    public MealDoesNotExistException() {
        super("The meal you looking for is not exist", HttpStatus.BAD_REQUEST);
    }
}