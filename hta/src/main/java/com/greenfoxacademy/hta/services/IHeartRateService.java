package com.greenfoxacademy.hta.services;

import com.greenfoxacademy.hta.models.HeartRate;
import org.springframework.stereotype.Service;

@Service
public interface IHeartRateService {
    void save(HeartRate heartRate, String email);
}
