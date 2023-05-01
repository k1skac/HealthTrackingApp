package com.greenfoxacademy.hta.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotificationNotFoundException extends HtaException {
    public NotificationNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}