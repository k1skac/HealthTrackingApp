package com.greenfoxacademy.hta.exceptions;

import org.springframework.http.HttpStatus;

public class ReadyFoodIsAlreadyExistException extends HtaException {
    public ReadyFoodIsAlreadyExistException() {
        super("The ready food is already exist", HttpStatus.BAD_REQUEST);
    }
}