package com.greenfoxacademy.hta.models.notifications;

import lombok.Getter;

@Getter
public enum NotificationMessage {
    BLOOD_PRESSURE("Please measure your blood pressure!"),
    HEART_RATE("Please measure your heart rate!"),
    MEDICATION("Don't forget to take your "),
    WEIGHT("Please measure your weight!");

    private final String message;

    NotificationMessage(String message) {
        this.message = message;
    }
}