package com.greenfoxacademy.hta.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Valid
public class RegisterDTO implements Serializable {
    String username;
 @Email
    String email;
    String password;
    String realName;
    String biologicalGender;
    LocalDate birthDate;
    double height;
}
