package com.greenfoxacademy.hta.exceptions.reportsexceptions;

import com.greenfoxacademy.hta.exceptions.HtaException;
import org.springframework.http.HttpStatus;

public class WeightNotFoundException extends HtaException {
    public WeightNotFoundException() {
        super("Cant find value", HttpStatus.NOT_FOUND);
    }
}
