package com.greenfoxacademy.hta.dtos.reportsdto.goal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseActiveGoalsDTO {

    private Date creationDate;
    private String goalDescription;
}
