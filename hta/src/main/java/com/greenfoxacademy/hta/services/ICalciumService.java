package com.greenfoxacademy.hta.services;

import com.greenfoxacademy.hta.models.bloodlabdata.Calcium;
import org.springframework.stereotype.Service;

@Service
public interface ICalciumService {
    void save(Calcium calcium, String email);
}
