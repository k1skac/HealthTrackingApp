package com.greenfoxacademy.hta.services.bloodlabdata;

import com.greenfoxacademy.hta.models.bloodlabdata.Calcium;
import org.springframework.stereotype.Service;

public interface ICalciumService {
    void save(Calcium calcium, String email);
}
