package com.greenfoxacademy.hta.exceptions;

import org.springframework.http.HttpStatus;

public class UserNotActiveForDeleteException extends HtaException {
    public UserNotActiveForDeleteException(String email) {
        super("The account of "+ email + " is already deactivated, nothing happend now", HttpStatus.NOT_MODIFIED);
    }
}
