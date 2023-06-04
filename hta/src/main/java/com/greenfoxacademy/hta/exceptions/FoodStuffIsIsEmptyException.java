package com.greenfoxacademy.hta.exceptions;

import org.springframework.http.HttpStatus;

public class FoodStuffIsIsEmptyException extends HtaException {
    public FoodStuffIsIsEmptyException() {
        super("The foodstuff list is empty", HttpStatus.NO_CONTENT);
    }
}