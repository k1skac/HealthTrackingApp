package com.greenfoxacademy.hta.exceptions;

import org.springframework.http.HttpStatus;

public class FoodStuffIsAlreadyExistException extends HtaException {
    public FoodStuffIsAlreadyExistException() {
        super("The foodstuff is already exist", HttpStatus.BAD_REQUEST);
    }
}