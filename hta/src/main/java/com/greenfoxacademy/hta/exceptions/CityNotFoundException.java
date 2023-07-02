package com.greenfoxacademy.hta.exceptions;

import org.springframework.http.HttpStatus;

public class CityNotFoundException extends HtaException{
    public CityNotFoundException(String cityName) {
        super(cityName + " is not an available settlement name!" , HttpStatus.BAD_REQUEST);
    }
}