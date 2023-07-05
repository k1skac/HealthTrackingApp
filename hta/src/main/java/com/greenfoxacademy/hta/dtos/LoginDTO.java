package com.greenfoxacademy.hta.dtos;

import jakarta.validation.constraints.Email;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginDTO {
    //it's a Data Transfer Object for Login
    @Email(message = "The entered email is not correct!")
    String email;
    String password;
}
