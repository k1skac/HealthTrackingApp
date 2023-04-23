package com.greenfoxacademy.hta.services;

import com.greenfoxacademy.hta.models.bloodlabdata.BloodLabData;
import org.springframework.stereotype.Service;

@Service
public interface IBloodLabDataService {
    void save(BloodLabData bloodLabData);

}
