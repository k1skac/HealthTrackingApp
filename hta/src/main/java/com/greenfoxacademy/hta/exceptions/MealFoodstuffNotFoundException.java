package com.greenfoxacademy.hta.exceptions;

import org.springframework.http.HttpStatus;

import java.util.function.Supplier;

public class MealFoodstuffNotFoundException extends HtaException {
    public MealFoodstuffNotFoundException(String name) {
        super("The foodstuff is not in the database: "+ name, HttpStatus.BAD_REQUEST);
    }
}