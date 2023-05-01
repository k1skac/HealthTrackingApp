package com.greenfoxacademy.hta.exceptions;

import org.springframework.http.HttpStatus;

public class UserEmailMissingException extends HtaException{
    public UserEmailMissingException() {
        super("Missing email address!", HttpStatus.BAD_REQUEST);
    }
}