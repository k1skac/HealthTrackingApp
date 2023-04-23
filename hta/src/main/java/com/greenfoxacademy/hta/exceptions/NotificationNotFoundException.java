package com.greenfoxacademy.hta.exceptions;

import org.springframework.http.HttpStatus;

public class NotificationNotFoundException extends HtaException {
    public NotificationNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}