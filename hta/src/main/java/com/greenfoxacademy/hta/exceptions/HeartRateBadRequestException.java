package com.greenfoxacademy.hta.exceptions;

public class HeartRateBadRequestException extends HtaException {
    public HeartRateBadRequestException() {
        super("Heart rate cannot be added!");
    }
}
