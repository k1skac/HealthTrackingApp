package com.greenfoxacademy.hta.exceptions.reportsexceptions;

import com.greenfoxacademy.hta.exceptions.HtaException;
import org.springframework.http.HttpStatus;

public class TriGlyceridesNotFoundException extends HtaException {
    public TriGlyceridesNotFoundException() {
        super("Cant find value", HttpStatus.NOT_FOUND);
    }
}
