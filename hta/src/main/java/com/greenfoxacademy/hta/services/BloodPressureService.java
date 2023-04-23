package com.greenfoxacademy.hta.services;

import com.greenfoxacademy.hta.models.BloodPressure;
import com.greenfoxacademy.hta.models.User;
import com.greenfoxacademy.hta.repositories.BloodPressureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BloodPressureService implements IBloodPressureService {
    private final BloodPressureRepository bloodPressureRepository;
    private final IUserService iUserService;

    @Autowired
    public BloodPressureService(BloodPressureRepository bloodPressureRepository, IUserService iUserService) {
        this.bloodPressureRepository = bloodPressureRepository;
        this.iUserService = iUserService;
    }

    @Override
    public void save(BloodPressure bloodPressure, String email) {
        User user = iUserService.findByEmail(email);
            bloodPressure.setUser(user);
            bloodPressureRepository.save(bloodPressure);
            iUserService.saverUser(user);
    }
}
