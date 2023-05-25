package com.greenfoxacademy.hta.dtos.groupdto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserNameDTO {
    private String dtoName;
}
