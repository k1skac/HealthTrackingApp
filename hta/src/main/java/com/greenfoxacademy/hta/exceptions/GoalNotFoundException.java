package com.greenfoxacademy.hta.exceptions;

import org.springframework.http.HttpStatus;

public class GoalNotFoundException extends HtaException {
    public GoalNotFoundException() {
        super("This goal does not exist", HttpStatus.NOT_FOUND);
    }
}
