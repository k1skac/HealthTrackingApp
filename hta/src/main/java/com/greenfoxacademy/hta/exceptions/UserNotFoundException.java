package com.greenfoxacademy.hta.exceptions;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends HtaException {
    public UserNotFoundException() {
        super("The user is not registered yet", HttpStatus.NOT_FOUND);
    }
}