package com.greenfoxacademy.hta.exceptions;

import org.springframework.http.HttpStatus;

public class MealNoFoodInItException extends HtaException {
    public MealNoFoodInItException() {
        super("The meal not contains any food or beverage", HttpStatus.BAD_REQUEST);
    }
}