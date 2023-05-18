package com.greenfoxacademy.hta.dtos.reportsdto.dateAndTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestDaysToPeriodAndHours {
    @NotNull(message = "StartDate may not be null")
    @JsonFormat(pattern = "YYYY-MM-dd")
    String startDate;
    @NotNull(message = "EndDate may not be null")
    @JsonFormat(pattern = "YYYY-MM-dd")
    String endDate;
    @NotNull(message = "StartDate may not be null")
    @Min(0)
    @Max(23)
    String startHour;
    @NotNull(message = "StartDate may not be null")
    @Min(0)
    @Max(23)
    String endHour;
}
