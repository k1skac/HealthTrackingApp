package com.greenfoxacademy.hta.exceptions;

import org.springframework.http.HttpStatus;

public class ExerciseTimeNoContentException extends HtaException {
    public ExerciseTimeNoContentException() {
        super("Exercise Time has no content", HttpStatus.NO_CONTENT);
    }
}
