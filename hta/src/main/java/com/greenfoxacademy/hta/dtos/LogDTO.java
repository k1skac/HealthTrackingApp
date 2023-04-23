package com.greenfoxacademy.hta.dtos;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LogDTO {
   LocalDateTime periodStartTime;
   LocalDateTime periodEndTime;
   String email;

   public LogDTO(LocalDateTime periodStartTime, LocalDateTime periodEndTime, String email) {
      this.periodStartTime = periodStartTime;
      this.periodEndTime = periodEndTime;
      this.email = email;
   }
}
