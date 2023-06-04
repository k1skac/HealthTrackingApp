package com.greenfoxacademy.hta.exceptions;

import org.springframework.http.HttpStatus;

public class MealReadyFoodNotFoundException extends HtaException {
    public MealReadyFoodNotFoundException(String name) {
        super("The ready food is not in the database: "+ name, HttpStatus.BAD_REQUEST);
    }
}