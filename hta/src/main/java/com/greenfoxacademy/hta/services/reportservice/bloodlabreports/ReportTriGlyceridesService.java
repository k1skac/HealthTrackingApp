package com.greenfoxacademy.hta.services.reportservice.bloodlabreports;

import com.greenfoxacademy.hta.dtos.reportsdto.labor.ResponseTriGlyceridesDTO;
import com.greenfoxacademy.hta.exceptions.HtaException;
import com.greenfoxacademy.hta.models.BloodLabDataLimits;
import com.greenfoxacademy.hta.models.bloodlabdata.TriGlycerides;
import com.greenfoxacademy.hta.exceptions.reportsexceptions.TriGlyceridesNotFoundException;
import com.greenfoxacademy.hta.repositories.reportrepository.IReportTriGlyceridesRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportTriGlyceridesService implements IReportTriGlyceridesService {

    private final IReportTriGlyceridesRepository IReportTriGlyceridesRepository;
    private final IReportBloodLabDataService IReportBloodLabDataService;

    public ReportTriGlyceridesService(IReportTriGlyceridesRepository IReportTriGlyceridesRepository, IReportBloodLabDataService IReportBloodLabDataService) {
        this.IReportTriGlyceridesRepository = IReportTriGlyceridesRepository;
        this.IReportBloodLabDataService = IReportBloodLabDataService;
    }

    public String getWarningMessage(double triGlyceridesValue, double maxTriGlyceridesValue) {
        String warning;
        if (triGlyceridesValue > maxTriGlyceridesValue) {
            warning = "The triGlycerides level is high!";
        } else {
            warning = "The triGlycerides level is correct!";
        }
        return warning;
    }

    public ResponseTriGlyceridesDTO getConvertedAndExtendedDTO(TriGlycerides triGlycerides, BloodLabDataLimits bloodLabDataLimits) {
        ResponseTriGlyceridesDTO responseTriGlyceridesDTO = new ResponseTriGlyceridesDTO();
        responseTriGlyceridesDTO.setValue(triGlycerides.getValue());
        responseTriGlyceridesDTO.setMeasureTime(triGlycerides.getMeasureTime());
        responseTriGlyceridesDTO.setMaxTriGlyceridesValue(bloodLabDataLimits.getMaxTriGlyceridesValue());
        String warning = getWarningMessage(triGlycerides.getValue(), bloodLabDataLimits.getMaxTriGlyceridesValue());
        responseTriGlyceridesDTO.setWarning(warning);
        return responseTriGlyceridesDTO;
    }

    @Override
    public ResponseTriGlyceridesDTO getLastTriGlyceridesLabor(Long laborId) {
        BloodLabDataLimits bloodLabDataLimits = new BloodLabDataLimits();
       TriGlycerides triGlycerides = IReportTriGlyceridesRepository.getLastTriGlyceridesData(laborId);
        return getConvertedAndExtendedDTO(triGlycerides, bloodLabDataLimits);
    }

    @Override
    public List<ResponseTriGlyceridesDTO> getLastSomeTriGlyceridesLabor(Authentication authentication, int neededLabor) throws HtaException {
        if (IReportTriGlyceridesRepository.countTriGlyceridesById(IReportBloodLabDataService.getBloodLabDataId(authentication)) > neededLabor) {
            neededLabor = Math.toIntExact(IReportTriGlyceridesRepository.countTriGlyceridesById(IReportBloodLabDataService.getBloodLabDataId(authentication)));
        }
        List<TriGlycerides> allTriGlycerides = IReportTriGlyceridesRepository.findAllByBloodLabDataId(IReportBloodLabDataService.getBloodLabDataId(authentication));
        if (!allTriGlycerides.isEmpty()) {
            BloodLabDataLimits bloodLabDataLimits = new BloodLabDataLimits();
            List<ResponseTriGlyceridesDTO> responseTriGlyceridesDTOList = new ArrayList<>();
            for (TriGlycerides triGlycerides : allTriGlycerides.subList(allTriGlycerides.size() - neededLabor + 1, allTriGlycerides.size())) {
                responseTriGlyceridesDTOList.add(getConvertedAndExtendedDTO(triGlycerides, bloodLabDataLimits));

            }
            return responseTriGlyceridesDTOList;
        }
        throw new TriGlyceridesNotFoundException();
    }

    @Override
    public List<ResponseTriGlyceridesDTO> getAllTriGlyceridesLabor(Authentication authentication) throws HtaException {
        if (!IReportTriGlyceridesRepository.findAllByBloodLabDataId(IReportBloodLabDataService.getBloodLabDataId(authentication)).isEmpty()) {
            BloodLabDataLimits bloodLabDataLimits = new BloodLabDataLimits();
            List<ResponseTriGlyceridesDTO> responseTriGlyceridesDTOList = new ArrayList<>();
            for (TriGlycerides triGlycerides : IReportTriGlyceridesRepository.findAllByBloodLabDataId(IReportBloodLabDataService.getBloodLabDataId(authentication))) {
                responseTriGlyceridesDTOList.add(getConvertedAndExtendedDTO(triGlycerides, bloodLabDataLimits));
            }
            return responseTriGlyceridesDTOList;
        }
        throw new TriGlyceridesNotFoundException();
    }

    @Override
    public List<ResponseTriGlyceridesDTO> getTriGlyceridesInPeriod(Authentication authentication, LocalDateTime startDate, LocalDateTime endDate) throws HtaException {
        BloodLabDataLimits bloodLabDataLimits = new BloodLabDataLimits();
        List<ResponseTriGlyceridesDTO> responseTriGlyceridesDTOList = new ArrayList<>();
        if (!IReportTriGlyceridesRepository.findAllTriGlyceridesLaborInPeriod(IReportBloodLabDataService.getBloodLabDataId(authentication), startDate, endDate).stream().toList().isEmpty()) {
            for (TriGlycerides triGlycerides : IReportTriGlyceridesRepository.findAllTriGlyceridesLaborInPeriod(IReportBloodLabDataService.getBloodLabDataId(authentication), startDate, endDate).stream().toList()) {
                responseTriGlyceridesDTOList.add(getConvertedAndExtendedDTO(triGlycerides, bloodLabDataLimits));
            }
            return responseTriGlyceridesDTOList;
        } else {
            throw new TriGlyceridesNotFoundException();
        }
    }
}
