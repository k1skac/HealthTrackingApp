package com.greenfoxacademy.hta.exceptions;

import org.springframework.http.HttpStatus;

public class WeightLossGoalNotFoundException extends HtaException{
    public WeightLossGoalNotFoundException() {
        super(HttpStatus.NOT_FOUND);
    }
}
