package com.greenfoxacademy.hta.services.reportservice.healthreports;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenfoxacademy.hta.dtos.reportsdto.health.ResponseBloodPressureDTO;
import com.greenfoxacademy.hta.dtos.reportsdto.health.ResponseSimpleBloodPressureDTO;
import com.greenfoxacademy.hta.exceptions.HtaException;
import com.greenfoxacademy.hta.models.BloodLabDataLimits;
import com.greenfoxacademy.hta.models.user.BloodPressure;
import com.greenfoxacademy.hta.exceptions.reportsexceptions.BloodPressureNotFoundException;
import com.greenfoxacademy.hta.repositories.IUserRepository;
import com.greenfoxacademy.hta.repositories.reportrepository.IReportBloodPressureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ReportBloodPressureService implements IReportBloodPressureService {
    private final IUserRepository iUserRepository;
    private final IReportBloodPressureRepository IReportBloodPressureRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public ReportBloodPressureService(IUserRepository iUserRepository, IReportBloodPressureRepository IReportBloodPressureRepository, ObjectMapper objectMapper) {
        this.iUserRepository = iUserRepository;
        this.IReportBloodPressureRepository = IReportBloodPressureRepository;
        this.objectMapper = objectMapper;
    }

    public Date getSqlDateFromLocalDateTime(LocalDateTime localDateTime) {
        Instant instant = localDateTime.atZone(ZoneId.of("Europe/London")).toInstant();
        java.util.Date utilDate = Date.from(instant);
        return new java.sql.Date(utilDate.getTime() );
    }

    public String getWarningMessage(float systolic, float diastolic,
                                    double systolicMax, double diastiolicMin) {
        String warning;
        if (systolic > systolicMax)
            warning = "The systolic level is high!";
        else if (diastolic < diastiolicMin) {
            warning = "The diastolic level is low!";
        } else if (systolic > systolicMax && diastolic < diastiolicMin) {
            warning = "The systolic level is high and the diastolic level is low!";
        } else {
            warning = "The blood pressure level is correct!";
        }
        return warning;
    }

    public ResponseBloodPressureDTO getConvertedAndExtendedDTO(BloodPressure bloodPressure, BloodLabDataLimits bloodLabDataLimits) {
        ResponseBloodPressureDTO responseBloodPressureDTO = new ResponseBloodPressureDTO();
        responseBloodPressureDTO.setSystolic(bloodPressure.getSystolic());
        responseBloodPressureDTO.setDiastolic(bloodPressure.getDiastolic());
        responseBloodPressureDTO.setMeasureTime(bloodPressure.getBloodPressureMeasuredAt());
        responseBloodPressureDTO.setSystolicMax(bloodLabDataLimits.getSystolicMax());
        responseBloodPressureDTO.setDiastiolicMin(bloodLabDataLimits.getDiastiolicMin());
        String warning = getWarningMessage(bloodPressure.getSystolic(), bloodPressure.getDiastolic(),
                bloodLabDataLimits.getSystolicMax(), bloodLabDataLimits.getDiastiolicMin());
        responseBloodPressureDTO.setWarning(warning);
        return responseBloodPressureDTO;
    }
    @Override
    public Long getCurrentUser(Authentication authentication) {
        return iUserRepository.findByEmail(authentication.getName()).get().getId();
    }

    @Override
    public List<ResponseBloodPressureDTO> getBloodPressureCurrentDay(Authentication authentication) throws HtaException {
        Long userId = getCurrentUser(authentication);
        LocalDateTime currentDate = LocalDateTime.now();
        Date convertedDate = getSqlDateFromLocalDateTime(currentDate);
        BloodLabDataLimits bloodLabDataLimits = new BloodLabDataLimits();
        List<ResponseBloodPressureDTO> responseBloodPressureDTOList = new ArrayList<>();
        if (!IReportBloodPressureRepository.getBloodPressureOnDay(userId, convertedDate).isEmpty()) {
            for (BloodPressure bloodPressure : IReportBloodPressureRepository.getBloodPressureOnDay(userId, convertedDate)) {
                responseBloodPressureDTOList.add(getConvertedAndExtendedDTO(bloodPressure, bloodLabDataLimits));
            }
            return responseBloodPressureDTOList;

        }
        throw new BloodPressureNotFoundException();
    }

    @Override
    public List<ResponseBloodPressureDTO> getBloodPressureOneDay(Authentication authentication, Date date) throws HtaException {
        Long userId = getCurrentUser(authentication);
        BloodLabDataLimits bloodLabDataLimits = new BloodLabDataLimits();
        List<ResponseBloodPressureDTO> responseBloodPressureDTOList = new ArrayList<>();
        if (!IReportBloodPressureRepository.getBloodPressureOnDay(userId, date).isEmpty()) {
            for (BloodPressure bloodPressure : IReportBloodPressureRepository.getBloodPressureOnDay(userId, date)) {
                responseBloodPressureDTOList.add(getConvertedAndExtendedDTO(bloodPressure, bloodLabDataLimits));
            }
            return responseBloodPressureDTOList;

        }
        throw new BloodPressureNotFoundException();
    }

    @Override
    public List<ResponseBloodPressureDTO> getBloodPressureOneDayByHours(Date date, String startHours, String endHours, Authentication authentication) throws HtaException {
        Long userId = getCurrentUser(authentication);
        BloodLabDataLimits bloodLabDataLimits = new BloodLabDataLimits();
        List<ResponseBloodPressureDTO> responseBloodPressureDTOList = new ArrayList<>();
        if (!IReportBloodPressureRepository.getBloodPressureOneDayByHours(date, startHours, endHours, userId).isEmpty()) {
            for (BloodPressure bloodPressure : IReportBloodPressureRepository.getBloodPressureOneDayByHours(date, startHours, endHours, userId)) {
                responseBloodPressureDTOList.add(getConvertedAndExtendedDTO(bloodPressure, bloodLabDataLimits));
            }
            return responseBloodPressureDTOList;

        }
        throw new BloodPressureNotFoundException();
    }

    @Override
    public List<ResponseBloodPressureDTO> getBloodPressureOnPeriod(Authentication authentication, Date startDate, Date endDate) throws HtaException {
        Long userId = getCurrentUser(authentication);
        BloodLabDataLimits bloodLabDataLimits = new BloodLabDataLimits();
        List<ResponseBloodPressureDTO> responseBloodPressureDTOList = new ArrayList<>();
        if (!IReportBloodPressureRepository.getBloodPressureOnPeriod(userId, startDate, endDate).isEmpty()) {
            for (BloodPressure bloodPressure : IReportBloodPressureRepository.getBloodPressureOnPeriod(userId, startDate, endDate)) {
                responseBloodPressureDTOList.add(getConvertedAndExtendedDTO(bloodPressure, bloodLabDataLimits));
            }
            return responseBloodPressureDTOList;

        }
        throw new BloodPressureNotFoundException();
    }

    @Override
    public List<ResponseBloodPressureDTO> getBloodPressureOnPeriodByHours(Authentication authentication, Date startDate, Date endDate, String startHours, String endHours) throws HtaException {
        Long userId = getCurrentUser(authentication);
        BloodLabDataLimits bloodLabDataLimits = new BloodLabDataLimits();
        List<ResponseBloodPressureDTO> responseBloodPressureDTOList = new ArrayList<>();
        if (!IReportBloodPressureRepository.getBloodPressureOnPeriodByHours(userId, startDate, endDate, startHours, endHours).isEmpty()) {
            for (BloodPressure bloodPressure : IReportBloodPressureRepository.getBloodPressureOnPeriodByHours(userId, startDate, endDate, startHours, endHours)) {
                responseBloodPressureDTOList.add(getConvertedAndExtendedDTO(bloodPressure, bloodLabDataLimits));
            }
            return responseBloodPressureDTOList;

        }
        throw new BloodPressureNotFoundException();
    }

    @Override
    public List<ResponseBloodPressureDTO> getBloodPressureOnLastWeek(Authentication authentication) throws HtaException {
        Long userId = getCurrentUser(authentication);
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = startDate.minusWeeks(1);
        BloodLabDataLimits bloodLabDataLimits = new BloodLabDataLimits();
        List<ResponseBloodPressureDTO> responseBloodPressureDTOList = new ArrayList<>();
        if (!IReportBloodPressureRepository.getBloodPressureOnLastWeek(userId, getSqlDateFromLocalDateTime(startDate), getSqlDateFromLocalDateTime(endDate)).isEmpty()) {
            for (BloodPressure bloodPressure : IReportBloodPressureRepository.getBloodPressureOnLastWeek(userId, getSqlDateFromLocalDateTime(startDate), getSqlDateFromLocalDateTime(endDate))) {
                responseBloodPressureDTOList.add(getConvertedAndExtendedDTO(bloodPressure, bloodLabDataLimits));
            }
            return responseBloodPressureDTOList;

        }
        throw new BloodPressureNotFoundException();
    }

    @Override
    public List<ResponseBloodPressureDTO> getBloodPressureOnLastWeekByHours(Authentication authentication, String startHours, String endHours) throws HtaException {
        Long userId = getCurrentUser(authentication);
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = startDate.minusWeeks(1);
        BloodLabDataLimits bloodLabDataLimits = new BloodLabDataLimits();
        List<ResponseBloodPressureDTO> responseBloodPressureDTOList = new ArrayList<>();
        if (!IReportBloodPressureRepository.getBloodPressureOnLastWeekOrMonthByHours(userId, getSqlDateFromLocalDateTime(startDate), getSqlDateFromLocalDateTime(endDate), startHours, endHours).isEmpty()) {
            for (BloodPressure bloodPressure : IReportBloodPressureRepository.getBloodPressureOnLastWeekOrMonthByHours(userId, getSqlDateFromLocalDateTime(startDate), getSqlDateFromLocalDateTime(endDate), startHours, endHours)) {
                responseBloodPressureDTOList.add(getConvertedAndExtendedDTO(bloodPressure, bloodLabDataLimits));
            }
            return responseBloodPressureDTOList;

        }
        throw new BloodPressureNotFoundException();
    }

    @Override
    public List<ResponseBloodPressureDTO> getBloodPressureOnLastMonthByHours(Authentication authentication, String startHours, String endHours) throws HtaException {
        Long userId = getCurrentUser(authentication);
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = startDate.minusMonths(1);
        BloodLabDataLimits bloodLabDataLimits = new BloodLabDataLimits();
        List<ResponseBloodPressureDTO> responseBloodPressureDTOList = new ArrayList<>();
        if (!IReportBloodPressureRepository.getBloodPressureOnLastWeekOrMonthByHours(userId, getSqlDateFromLocalDateTime(startDate), getSqlDateFromLocalDateTime(endDate), startHours, endHours).isEmpty()) {
            for (BloodPressure bloodPressure : IReportBloodPressureRepository.getBloodPressureOnLastWeekOrMonthByHours(userId, getSqlDateFromLocalDateTime(startDate), getSqlDateFromLocalDateTime(endDate), startHours, endHours)) {
                responseBloodPressureDTOList.add(getConvertedAndExtendedDTO(bloodPressure, bloodLabDataLimits));
            }
            return responseBloodPressureDTOList;

        }
        throw new BloodPressureNotFoundException();
    }

    @Override
    public ResponseBloodPressureDTO getMaxBloodPressureOnDay(Authentication authentication, Date date) throws HtaException {
        Long userId = getCurrentUser(authentication);
        BloodLabDataLimits bloodLabDataLimits = new BloodLabDataLimits();
        if(!Objects.isNull(IReportBloodPressureRepository.getMaxSystolicOnDay(date, userId))) {
            BloodPressure maxSystolicBloodPressure = IReportBloodPressureRepository.getMaxSystolicOnDay(date, userId);
            return getConvertedAndExtendedDTO(maxSystolicBloodPressure, bloodLabDataLimits);
        }
        throw new BloodPressureNotFoundException();
    }

    @Override
    public List<ResponseBloodPressureDTO> getMaxBloodPressureByDayLastWeek(Authentication authentication) throws HtaException {
        Long userId = getCurrentUser(authentication);
        BloodLabDataLimits bloodLabDataLimits = new BloodLabDataLimits();
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime beforeDay = startDate;
        LocalDateTime endDate = startDate.minusWeeks(1);
        List<ResponseBloodPressureDTO> responseMaxBloodPressureDTOList = new ArrayList<>();
        while (!beforeDay.equals(endDate)) {
            BloodPressure maxBloodPressureOnDay = IReportBloodPressureRepository.getMaxSystolicOnDay(getSqlDateFromLocalDateTime(beforeDay), userId);
            if (!Objects.isNull(maxBloodPressureOnDay)) {
                responseMaxBloodPressureDTOList.add(getConvertedAndExtendedDTO(maxBloodPressureOnDay, bloodLabDataLimits));
            }
            beforeDay = beforeDay.minusDays(1);
        }
        if(!responseMaxBloodPressureDTOList.isEmpty()) {
            return responseMaxBloodPressureDTOList;
        } else {
            throw new BloodPressureNotFoundException();
        }
    }
    @Override
    public List<ResponseSimpleBloodPressureDTO> getLastSevenBloodPressure(Authentication authentication) throws HtaException {
        Long userId = getCurrentUser(authentication);
        if(!IReportBloodPressureRepository.findTop7ByUserIdOrderByBloodPressureMeasuredAt(userId).isEmpty()) {
            return IReportBloodPressureRepository.findTop7ByUserIdOrderByBloodPressureMeasuredAt(userId).stream()
                    .map(bloodPressure -> objectMapper.convertValue(bloodPressure, ResponseSimpleBloodPressureDTO.class))
                    .toList();
        }
        throw new BloodPressureNotFoundException();
    }
}
