package com.greenfoxacademy.hta.dtos.reportsdto.dateAndTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestDaysToPeriodDTO {
    @NotNull(message = "StartDate may not be null")
    @JsonFormat(pattern = "YYYY-MM-dd")
    String startDate;
    @NotNull(message = "EndDate may not be null")
    @JsonFormat(pattern = "YYYY-MM-dd")
    String endDate;
}
