package com.greenfoxacademy.hta.exceptions;

import org.springframework.http.HttpStatus;

public class HtaException extends Exception{
  private final HttpStatus status;

  public HtaException(String message, HttpStatus status) {
    super(message);
    this.status = status;
  }
  public HtaException(String message) {
    super(message);
    this.status = HttpStatus.BAD_REQUEST;
  }
}