package com.greenfoxacademy.hta.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public class DailyCalorieIntakeNotFoundException extends HtaException {
    public DailyCalorieIntakeNotFoundException() {
        super("Cant find the value", HttpStatus.NOT_FOUND);
    }
}
