package com.greenfoxacademy.hta.dtos;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginDto {

    //it's a Data Transfer Object for Login
    String email ;
    String password ;
}
