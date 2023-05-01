package com.greenfoxacademy.hta.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotificationConflictException extends HtaException {
    public NotificationConflictException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}