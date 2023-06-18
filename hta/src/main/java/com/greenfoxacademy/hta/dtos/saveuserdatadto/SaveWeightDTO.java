package com.greenfoxacademy.hta.dtos.saveuserdatadto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SaveWeightDTO {
    private float weight;
    private LocalDateTime weightMeasuredAt;
    private MultipartFile weightFile;
}
