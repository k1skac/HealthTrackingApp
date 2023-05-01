package com.greenfoxacademy.hta.exceptions;

import org.springframework.http.HttpStatus;

public class UserEmailAlreadyTakenException extends HtaException {
    public UserEmailAlreadyTakenException() {
        super("The email is already taken", HttpStatus.BAD_REQUEST);
    }
}
