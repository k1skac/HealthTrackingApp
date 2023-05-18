package com.greenfoxacademy.hta.services.reportservice.bloodlabreports;

import com.greenfoxacademy.hta.models.bloodlabdata.Glucose;
import com.greenfoxacademy.hta.exceptions.reportsexceptions.GlucoseNotFoundException;
import com.greenfoxacademy.hta.dtos.reportsdto.labor.ResponseGlucoseDTO;
import com.greenfoxacademy.hta.exceptions.HtaException;
import com.greenfoxacademy.hta.models.BloodLabDataLimits;
import com.greenfoxacademy.hta.repositories.reportrepository.IReportGlucoseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportGlucoseService implements IReportGlucoseService {

    private final IReportGlucoseRepository IReportGlucoseRepository;
    private final IReportBloodLabDataService IReportBloodLabDataService;

    @Autowired
    public ReportGlucoseService(IReportGlucoseRepository IReportGlucoseRepository, IReportBloodLabDataService IReportBloodLabDataService) {
        this.IReportGlucoseRepository = IReportGlucoseRepository;
        this.IReportBloodLabDataService = IReportBloodLabDataService;
    }

    public String getWarningMessage(double glucoseValue, double glucoseMaxValue, double glucoseMinvalue) {
        String warning;
        if (glucoseValue > glucoseMaxValue)
            warning = "The glucose level is high!";
        else if (glucoseValue < glucoseMinvalue) {
            warning = "The glucose level is low!";
        } else {
            warning = "The glucose level is correct!";
        }
        return warning;
    }

    public ResponseGlucoseDTO getConvertedAndExtendedDTO(Glucose glucose, BloodLabDataLimits bloodLabDataLimits) {
        ResponseGlucoseDTO responseGlucoseDTO = new ResponseGlucoseDTO();
        responseGlucoseDTO.setValueOfGlucose(glucose.getValue());
        responseGlucoseDTO.setMeasureTime(glucose.getMeasureTime());
        responseGlucoseDTO.setMaxGlucoseValue(bloodLabDataLimits.getMaxGlucoseValue());
        responseGlucoseDTO.setMinGlucoseValue(bloodLabDataLimits.getMinGlucoseValue());
        String warning = getWarningMessage(glucose.getValue(), bloodLabDataLimits.getMaxGlucoseValue(), bloodLabDataLimits.getMinGlucoseValue());
        responseGlucoseDTO.setWarning(warning);
        return responseGlucoseDTO;
    }

    @Override
    public ResponseGlucoseDTO getLastGlucoseLabor(Long laborId) {
        BloodLabDataLimits bloodLabDataLimits = new BloodLabDataLimits();
        Glucose glucose = IReportGlucoseRepository.getLastGlucoseData(laborId);
        return getConvertedAndExtendedDTO(glucose, bloodLabDataLimits);
    }

    @Override
    public List<ResponseGlucoseDTO> getLastSomeGlucoseLabor(Authentication authentication, int neededLabor) throws HtaException{
        if (IReportGlucoseRepository.countGlucoseById(IReportBloodLabDataService.getBloodLabDataId(authentication)) > neededLabor) {
            neededLabor = Math.toIntExact(IReportGlucoseRepository.countGlucoseById(IReportBloodLabDataService.getBloodLabDataId(authentication)));
        }
        List<Glucose> allGlucose = IReportGlucoseRepository.findAllByBloodLabDataId(IReportBloodLabDataService.getBloodLabDataId(authentication));
        if (!allGlucose.isEmpty()) {
            BloodLabDataLimits bloodLabDataLimits = new BloodLabDataLimits();
            List<ResponseGlucoseDTO> responseGlucoseDTOList = new ArrayList<>();
            System.out.println(allGlucose.size());
            for (Glucose glucose : allGlucose.subList(allGlucose.size() - neededLabor + 1, allGlucose.size())) {
                responseGlucoseDTOList.add(getConvertedAndExtendedDTO(glucose, bloodLabDataLimits));
            }
            return responseGlucoseDTOList;
        }
        throw new GlucoseNotFoundException();
    }

    @Override
    public List<ResponseGlucoseDTO> getAllGlucoseLabor(Authentication authentication) throws HtaException {
        if (!IReportGlucoseRepository.findAllByBloodLabDataId(IReportBloodLabDataService.getBloodLabDataId(authentication)).isEmpty()) {
            BloodLabDataLimits bloodLabDataLimits = new BloodLabDataLimits();
            List<ResponseGlucoseDTO> responseGlucoseDTOList = new ArrayList<>();
            for (Glucose glucose : IReportGlucoseRepository.findAllByBloodLabDataId(IReportBloodLabDataService.getBloodLabDataId(authentication))) {
                responseGlucoseDTOList.add(getConvertedAndExtendedDTO(glucose, bloodLabDataLimits));
            }
            return responseGlucoseDTOList;
        }
        throw new GlucoseNotFoundException();
    }

    @Override
    public List<ResponseGlucoseDTO> getGlucoseInPeriod(Authentication authentication, LocalDateTime startDate, LocalDateTime endDate) throws HtaException {
        BloodLabDataLimits bloodLabDataLimits = new BloodLabDataLimits();
        List<ResponseGlucoseDTO> responseGlucoseDTOList = new ArrayList<>();
        if (!IReportGlucoseRepository.findAllGlucoseLaborInPeriod(IReportBloodLabDataService.getBloodLabDataId(authentication), startDate, endDate).stream().toList().isEmpty()) {
            for (Glucose glucose : IReportGlucoseRepository.findAllGlucoseLaborInPeriod(IReportBloodLabDataService.getBloodLabDataId(authentication), startDate, endDate).stream().toList()) {
                responseGlucoseDTOList.add(getConvertedAndExtendedDTO(glucose, bloodLabDataLimits));
            }
            return responseGlucoseDTOList;
        } else {
            throw new GlucoseNotFoundException();
        }
    }
}
