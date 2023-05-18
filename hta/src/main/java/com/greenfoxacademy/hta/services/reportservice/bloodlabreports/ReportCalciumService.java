package com.greenfoxacademy.hta.services.reportservice.bloodlabreports;

import com.greenfoxacademy.hta.dtos.reportsdto.labor.ResponseCalciumDTO;
import com.greenfoxacademy.hta.models.bloodlabdata.Calcium;
import com.greenfoxacademy.hta.exceptions.reportsexceptions.CalciumNotFoundException;
import com.greenfoxacademy.hta.exceptions.HtaException;
import com.greenfoxacademy.hta.models.BloodLabDataLimits;

import com.greenfoxacademy.hta.repositories.reportrepository.IReportCalciumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class ReportCalciumService implements IReportCalciumService {
    private final IReportCalciumRepository IReportCalciumRepository;
    private final IReportBloodLabDataService IReportBloodLabDataService;

    @Autowired
    public ReportCalciumService(IReportCalciumRepository IReportCalciumRepository, IReportBloodLabDataService IReportBloodLabDataService) {
        this.IReportCalciumRepository = IReportCalciumRepository;
        this.IReportBloodLabDataService = IReportBloodLabDataService;
    }

    public String getWarningMessage(double calciumValue, double calciumMaxValue, double calciumMinvalue) {
        String warning;
        if (calciumValue > calciumMaxValue)
            warning = "The calcium level is high!";
        else if (calciumValue < calciumMinvalue) {
            warning = "The calcium level is low!";
        } else {
            warning = "The calcium level is correct!";
        }
        return warning;
    }
    public ResponseCalciumDTO getConvertedAndExtendedDTO(Calcium calcium, BloodLabDataLimits bloodLabDataLimits) {
        ResponseCalciumDTO responseCalciumDTO = new ResponseCalciumDTO();
        responseCalciumDTO.setValueOfCalcium(calcium.getValue());
        responseCalciumDTO.setMeasureTime(calcium.getMeasureTime());
        responseCalciumDTO.setMaxCalciumValue(bloodLabDataLimits.getMaxCalciumValue());
        responseCalciumDTO.setMinCalciumValue(bloodLabDataLimits.getMinCalciumValue());
        String warning = getWarningMessage(calcium.getValue(), bloodLabDataLimits.getMaxCalciumValue(), bloodLabDataLimits.getMinCalciumValue());
        responseCalciumDTO.setWarning(warning);
        return responseCalciumDTO;
    }


    @Override
    public ResponseCalciumDTO getLastCalciumLabor(Long laborId) {
        BloodLabDataLimits bloodLabDataLimits = new BloodLabDataLimits();
        Calcium calcium = IReportCalciumRepository.getLastCalciumData(laborId);
        return getConvertedAndExtendedDTO(calcium, bloodLabDataLimits);
    }

    @Override
    public List<ResponseCalciumDTO> getLastSomeCalciumLabor(Authentication authentication, int neededLabor) throws HtaException{
        if (IReportCalciumRepository.countCalciumById(IReportBloodLabDataService.getBloodLabDataId(authentication)) > neededLabor) {
            neededLabor = Math.toIntExact(IReportCalciumRepository.countCalciumById(IReportBloodLabDataService.getBloodLabDataId(authentication)));
        }
        List<Calcium> allCalcium = IReportCalciumRepository.findAllByBloodLabDataId(IReportBloodLabDataService.getBloodLabDataId(authentication));
        if (!allCalcium.isEmpty()) {
            BloodLabDataLimits bloodLabDataLimits = new BloodLabDataLimits();
            List<ResponseCalciumDTO> responseCalciumDTOList = new ArrayList<>();
            for (Calcium calcium : allCalcium.subList(allCalcium.size() - neededLabor + 1, allCalcium.size())) {
                responseCalciumDTOList.add(getConvertedAndExtendedDTO(calcium, bloodLabDataLimits));
            }
            return responseCalciumDTOList;
        }
        throw new CalciumNotFoundException();
    }

    @Override
    public List<ResponseCalciumDTO> getAllCalciumLabor(Authentication authentication) throws HtaException {
        if (!IReportCalciumRepository.findAllByBloodLabDataId(IReportBloodLabDataService.getBloodLabDataId(authentication)).isEmpty()) {
            BloodLabDataLimits bloodLabDataLimits = new BloodLabDataLimits();
            List<ResponseCalciumDTO> responseCalciumDTOList = new ArrayList<>();
            for (Calcium calcium : IReportCalciumRepository.findAllByBloodLabDataId(IReportBloodLabDataService.getBloodLabDataId(authentication))) {
                responseCalciumDTOList.add(getConvertedAndExtendedDTO(calcium, bloodLabDataLimits));
            }
            return responseCalciumDTOList;
        }
        throw new CalciumNotFoundException();
   }

    @Override
    public List<ResponseCalciumDTO> getCalciumInPeriod(Authentication authentication, LocalDateTime startDate, LocalDateTime endDate) throws HtaException {
        BloodLabDataLimits bloodLabDataLimits = new BloodLabDataLimits();
        List<ResponseCalciumDTO> responseCalciumDTOList = new ArrayList<>();
        if (!IReportCalciumRepository.findAllCalciumLaborInPeriod(IReportBloodLabDataService.getBloodLabDataId(authentication), startDate, endDate).stream().toList().isEmpty()) {
            for (Calcium calcium : IReportCalciumRepository.findAllCalciumLaborInPeriod(IReportBloodLabDataService.getBloodLabDataId(authentication), startDate, endDate).stream().toList()) {
                responseCalciumDTOList.add(getConvertedAndExtendedDTO(calcium, bloodLabDataLimits));
            }
            return responseCalciumDTOList;
        } else {
            throw new CalciumNotFoundException();
        }
    }
}
