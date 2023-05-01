package com.greenfoxacademy.hta.services.bloodlabdata;

import com.greenfoxacademy.hta.models.bloodlabdata.BloodLabData;
import com.greenfoxacademy.hta.models.bloodlabdata.Calcium;
import com.greenfoxacademy.hta.models.user.User;
import com.greenfoxacademy.hta.repositories.bloodlabdata.ICalciumRepository;
import com.greenfoxacademy.hta.services.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CalciumService implements ICalciumService {
    private final IUserService iUserService;
    private final ICalciumRepository iCalciumRepository;
    private final IBloodLabDataService iBloodLabDataService;

    @Override
    public void save(Calcium calcium, String email) {
        User user = iUserService.findByEmail(email);
        if (user.getBloodLabData() == null){
            BloodLabData bloodLabData = new BloodLabData();
            bloodLabData.setUser(user);
            user.setBloodLabData(bloodLabData);
        }
        BloodLabData bloodLabData = user.getBloodLabData();
        if (bloodLabData.getCalciumValues() == null){
            List<Calcium> calciumValues = new ArrayList<>();
            bloodLabData.setCalciumValues(calciumValues);
            iBloodLabDataService.save(bloodLabData);
        }
        List<Calcium> calciumValues = bloodLabData.getCalciumValues();
        calcium.setBloodLabData(bloodLabData);
        calciumValues.add(calcium);
        iCalciumRepository.save(calcium);
        iBloodLabDataService.save(bloodLabData);
    }
}
