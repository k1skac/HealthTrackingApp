package com.greenfoxacademy.hta.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public class DailyCalorieIntakeNoContentException extends HtaException{

    public DailyCalorieIntakeNoContentException() {
        super("Please give a Calorie value!", HttpStatus.NO_CONTENT);
    }
}
