package com.greenfoxacademy.hta.exceptions;

public class BloodPressureBadRequestException extends HtaException {
    public BloodPressureBadRequestException() {
        super("Blood pressure cannot be added!");
    }
}
