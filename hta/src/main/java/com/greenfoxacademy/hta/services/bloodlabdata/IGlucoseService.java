package com.greenfoxacademy.hta.services.bloodlabdata;

import com.greenfoxacademy.hta.models.bloodlabdata.Glucose;
import org.springframework.stereotype.Service;

public interface IGlucoseService {
    void save(Glucose glucose, String email);
}
