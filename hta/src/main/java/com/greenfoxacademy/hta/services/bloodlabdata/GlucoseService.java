package com.greenfoxacademy.hta.services.bloodlabdata;

import com.greenfoxacademy.hta.models.bloodlabdata.BloodLabData;
import com.greenfoxacademy.hta.models.bloodlabdata.Glucose;
import com.greenfoxacademy.hta.models.user.User;
import com.greenfoxacademy.hta.repositories.bloodlabdata.IGlucoseRepository;
import com.greenfoxacademy.hta.services.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class GlucoseService implements IGlucoseService {
    private final IGlucoseRepository iGlucoseRepository;
    private final IUserService iUserService;
    private final IBloodLabDataService iBloodLabDataService;

    @Autowired
    public GlucoseService(IGlucoseRepository iGlucoseRepository, IUserService iUserService, IBloodLabDataService iBloodLabDataService) {
        this.iGlucoseRepository = iGlucoseRepository;
        this.iUserService = iUserService;
        this.iBloodLabDataService = iBloodLabDataService;
    }

    @Override
    public void save(Glucose glucose, String email) {
        User user = iUserService.findByEmail(email);
            if (user.getBloodLabData() == null){
                BloodLabData bloodLabData = new BloodLabData();
                bloodLabData.setUser(user);
                user.setBloodLabData(bloodLabData);
            }
            BloodLabData bloodLabData = user.getBloodLabData();
            if (bloodLabData.getGlucoseValues() == null){
                List<Glucose> glucoseValues = new ArrayList<>();
                bloodLabData.setGlucoseValues(glucoseValues);
                iBloodLabDataService.save(bloodLabData);
            }
            List<Glucose> glucoseValues = bloodLabData.getGlucoseValues();
            glucoseValues.add(glucose);
            glucose.setBloodLabData(bloodLabData);
            iBloodLabDataService.save(bloodLabData);
            iGlucoseRepository.save(glucose);
    }
}
