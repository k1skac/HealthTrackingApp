package com.greenfoxacademy.hta.dtos;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateProfileDTO implements Serializable {
    String username;
    String realName;
    String biologicalGender;
    LocalDate birthDate;
    String cityName;
    double height;
}
