package com.greenfoxacademy.hta.services;

import com.greenfoxacademy.hta.models.bloodlabdata.BloodLabData;
import com.greenfoxacademy.hta.repositories.BloodLabDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BloodLabDataService implements IBloodLabDataService {
    private final BloodLabDataRepository bloodLabDataRepository;

    @Override
    public void save(BloodLabData bloodLabData) {
        bloodLabDataRepository.save(bloodLabData);
    }
}
