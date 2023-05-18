package com.greenfoxacademy.hta.exceptions.reportsexceptions;

import com.greenfoxacademy.hta.exceptions.HtaException;
import org.springframework.http.HttpStatus;

public class BloodPressureNotFoundException extends HtaException {
    public BloodPressureNotFoundException() {
        super("Cant find value", HttpStatus.NOT_FOUND);
    }
}
