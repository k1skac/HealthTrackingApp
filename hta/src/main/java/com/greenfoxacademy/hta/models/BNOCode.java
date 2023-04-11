package com.greenfoxacademy.hta.models;

public enum BNOCode {

    K7030("Alkoholos májzsugorodás"),
    L8630("Bőrszárazság"),
    M2520("Lötyögő izület"),
    M2550("Izületi fájdalom");

    private String description;

  BNOCode(String description) {
    this.description = description;
  }
}
