package com.greenfoxacademy.hta.dtos.reportsdto.dateAndTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestDateDTO {
    @JsonFormat(pattern="yyyy-MM-dd")
    private String selectedDate;
}
