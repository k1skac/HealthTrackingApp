package com.greenfoxacademy.hta.services.reportservice.bloodlabreports;

import com.greenfoxacademy.hta.dtos.reportsdto.labor.ResponseLastBloodDataLabDTO;
import com.greenfoxacademy.hta.repositories.IUserRepository;
import com.greenfoxacademy.hta.repositories.reportrepository.IReportBloodLabDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


@Service
public class ReportBloodLabDataService implements IReportBloodLabDataService {

    private final IReportBloodLabDataRepository IReportBloodLabDataRepository;
    private final IUserRepository iUserRepository;
    private final IReportCalciumService IReportCalciumService;
    private final IReportGlucoseService IReportGlucoseService;
    private final IReportTriGlyceridesService IReportTriGlyceridesService;

    @Autowired
    public ReportBloodLabDataService(IReportBloodLabDataRepository IReportBloodLabDataRepository, IUserRepository iUserRepository,
                                     @Lazy IReportCalciumService IReportCalciumService,
                                     @Lazy IReportGlucoseService IReportGlucoseService,
                                     @Lazy IReportTriGlyceridesService IReportTriGlyceridesService) {
        this.IReportBloodLabDataRepository = IReportBloodLabDataRepository;
        this.iUserRepository = iUserRepository;
        this.IReportCalciumService = IReportCalciumService;
        this.IReportGlucoseService = IReportGlucoseService;
        this.IReportTriGlyceridesService = IReportTriGlyceridesService;
    }

    @Override
    public Long getCurrentUser(Authentication authentication) {
        return iUserRepository.findByEmail(authentication.getName()).get().getId();
    }

    @Override
    public Long getBloodLabDataId(Authentication authentication) {
        Long userId = getCurrentUser(authentication);
        return IReportBloodLabDataRepository.getBloodLabDataId(userId);
    }

    @Override
    public ResponseLastBloodDataLabDTO getLastAllBloodLabData(Authentication authentication) {
        ResponseLastBloodDataLabDTO responseLastBloodDataLabDTO = new ResponseLastBloodDataLabDTO();
        responseLastBloodDataLabDTO.setLastCalciumLaborData(IReportCalciumService.getLastCalciumLabor(getBloodLabDataId(authentication)));
        responseLastBloodDataLabDTO.setLastGlucoseLaborData(IReportGlucoseService.getLastGlucoseLabor(getBloodLabDataId(authentication)));
        responseLastBloodDataLabDTO.setLastTriGlycerideLaborData(IReportTriGlyceridesService.getLastTriGlyceridesLabor(getBloodLabDataId(authentication)));
        return responseLastBloodDataLabDTO;
    }

}
