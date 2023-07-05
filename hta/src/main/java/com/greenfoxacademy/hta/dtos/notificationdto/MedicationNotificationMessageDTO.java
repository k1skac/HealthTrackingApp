package com.greenfoxacademy.hta.dtos.notificationdto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MedicationNotificationMessageDTO {
    private List<String> medicationMessages;
}
