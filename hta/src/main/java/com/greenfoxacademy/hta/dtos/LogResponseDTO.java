package com.greenfoxacademy.hta.dtos;

import com.greenfoxacademy.hta.models.log.Log;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LogResponseDTO {

    List<Log> loglist;

    public LogResponseDTO(List<Log> loglist) {
        this.loglist = loglist;
    }
}
