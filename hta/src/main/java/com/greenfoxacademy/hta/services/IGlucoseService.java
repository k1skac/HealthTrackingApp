package com.greenfoxacademy.hta.services;

import com.greenfoxacademy.hta.models.bloodlabdata.Glucose;
import org.springframework.stereotype.Service;

@Service
public interface IGlucoseService {
    void save(Glucose glucose, String email);
}
