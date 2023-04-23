package com.greenfoxacademy.hta.services;

import com.greenfoxacademy.hta.models.BloodPressure;
import org.springframework.stereotype.Service;

@Service
public interface IBloodPressureService {
    void save(BloodPressure bloodPressure, String email);
}
