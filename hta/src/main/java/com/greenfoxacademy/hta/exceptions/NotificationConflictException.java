package com.greenfoxacademy.hta.exceptions;

import org.springframework.http.HttpStatus;

public class NotificationConflictException extends HtaException {
    public NotificationConflictException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}