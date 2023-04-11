package com.greenfoxacademy.hta.dtos;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterDto implements Serializable {

    //it's a Data Transfer Object for registration
    String username;
    String email;
    String password;
    String realName;
    String biologicalGender;
    LocalDate birthDate;
    double height;



}
