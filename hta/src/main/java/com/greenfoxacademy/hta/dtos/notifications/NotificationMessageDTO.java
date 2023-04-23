package com.greenfoxacademy.hta.dtos.notifications;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NotificationMessageDTO {
    private List<String> messages;
}
