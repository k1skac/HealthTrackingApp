package com.greenfoxacademy.hta.services.reportservice.healthreports;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenfoxacademy.hta.dtos.reportsdto.goal.ResponseHeartRateDTO;
import com.greenfoxacademy.hta.exceptions.HtaException;
import com.greenfoxacademy.hta.models.user.HeartRate;
import com.greenfoxacademy.hta.exceptions.reportsexceptions.HeartRateNotFoundException;
import com.greenfoxacademy.hta.repositories.IUserRepository;
import com.greenfoxacademy.hta.repositories.reportrepository.IReportHeartRateRepository;
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
public class ReportHeartRateService implements IReportHeartRateService {

    private final IReportHeartRateRepository IReportHeartRateRepository;
    private final IUserRepository iUserRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public ReportHeartRateService(IReportHeartRateRepository IReportHeartRateRepository, IUserRepository iUserRepository, ObjectMapper objectMapper) {
        this.IReportHeartRateRepository = IReportHeartRateRepository;
        this.iUserRepository = iUserRepository;
        this.objectMapper = objectMapper;
    }

    public Date getSqlDateFromLocalDateTime(LocalDateTime localDateTime) {
        Instant instant = localDateTime.atZone(ZoneId.of("Europe/London")).toInstant();
        java.util.Date utilDate = Date.from(instant);
        return new java.sql.Date(utilDate.getTime() );
    }
    @Override
    public Long getCurrentUser(Authentication authentication) {
        return iUserRepository.findByEmail(authentication.getName()).get().getId();
    }

    @Override
    public List<ResponseHeartRateDTO> getHeartRateCurrentDay(Authentication authentication) throws HtaException{
        Long userId = getCurrentUser(authentication);
        LocalDateTime currentDate = LocalDateTime.now();
        Date convertedDate = getSqlDateFromLocalDateTime(currentDate);
        if (!IReportHeartRateRepository.getHeartRateOnDay(userId, convertedDate).isEmpty()) {
            return IReportHeartRateRepository.getHeartRateOnDay(userId, convertedDate).stream()
                    .map(heartRate -> objectMapper.convertValue(heartRate, ResponseHeartRateDTO.class))
                    .toList();
        }
        throw new HeartRateNotFoundException();
    }

    @Override
    public List<ResponseHeartRateDTO> getHeartRateOneDay(Authentication authentication, Date date) throws HtaException {
        Long userId = getCurrentUser(authentication);
        if (!IReportHeartRateRepository.getHeartRateOnDay(userId, date).isEmpty()) {
            return IReportHeartRateRepository.getHeartRateOnDay(userId, date).stream()
                    .map(heartRate -> objectMapper.convertValue(heartRate, ResponseHeartRateDTO.class))
                    .toList();
        }
        throw new HeartRateNotFoundException();
    }

    @Override
    public List<ResponseHeartRateDTO> getHeartRateOneDayByHours(Date date, String startHours, String endHours, Authentication authentication) throws HtaException{
        Long userId = getCurrentUser(authentication);
        if (!IReportHeartRateRepository.getHeartRateOneDayByHours(date, startHours, endHours, userId).isEmpty()) {
            return IReportHeartRateRepository.getHeartRateOneDayByHours(date, startHours, endHours, userId).stream()
                    .map(heartRate -> objectMapper.convertValue(heartRate, ResponseHeartRateDTO.class))
                    .toList();
        }
        throw new HeartRateNotFoundException();
    }

    @Override
    public List<ResponseHeartRateDTO> getHeartRateOnPeriod(Authentication authentication, Date startDate, Date endDate) throws HtaException{
        Long userId = getCurrentUser(authentication);
        if (!IReportHeartRateRepository.getHeartRateOnPeriod(userId, startDate, endDate).isEmpty()) {
            return IReportHeartRateRepository.getHeartRateOnPeriod(userId, startDate, endDate).stream()
                    .map(heartRate -> objectMapper.convertValue(heartRate, ResponseHeartRateDTO.class))
                    .toList();
        }
        throw new HeartRateNotFoundException();
    }

    @Override
    public List<ResponseHeartRateDTO> getHeartRateOnPeriodByHours(Authentication authentication, Date startDate, Date endDate, String startHours, String endHours) throws HtaException {
        Long userId = getCurrentUser(authentication);
        if (!IReportHeartRateRepository.getHeartRateOnPeriodByHours(userId, startDate, endDate, startHours, endHours).isEmpty()) {
            return IReportHeartRateRepository.getHeartRateOnPeriodByHours(userId, startDate, endDate, startHours, endHours).stream()
                    .map(heartRate -> objectMapper.convertValue(heartRate, ResponseHeartRateDTO.class))
                    .toList();
        }
        throw new HeartRateNotFoundException();
    }

    @Override
    public List<ResponseHeartRateDTO> getHeartRateOnLastWeek(Authentication authentication) throws HtaException {
        Long userId = getCurrentUser(authentication);
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = startDate.minusWeeks(1);
        List<HeartRate> heartRateOnLastWeek = IReportHeartRateRepository.getHeartRateOnLastWeek(userId, getSqlDateFromLocalDateTime(startDate), getSqlDateFromLocalDateTime(endDate));
        if(!heartRateOnLastWeek .isEmpty()) {
            return heartRateOnLastWeek .stream()
                    .map(heartRate -> objectMapper.convertValue(heartRate, ResponseHeartRateDTO.class))
                    .toList();
        } else {
            throw new HeartRateNotFoundException();
        }
    }

    @Override
    public List<ResponseHeartRateDTO> getHeartRateOnLastWeekByHours(Authentication authentication, String startHours, String endHours) throws HtaException{
        Long userId = getCurrentUser(authentication);
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = startDate.minusWeeks(1);
        List<HeartRate> heartRateByHoursLastWeek = IReportHeartRateRepository.getHeartRateOnLastWeekOrMonthByHours(userId,getSqlDateFromLocalDateTime(startDate), getSqlDateFromLocalDateTime(endDate), startHours, endHours);
        if(!heartRateByHoursLastWeek.isEmpty()) {
            return heartRateByHoursLastWeek.stream()
                    .map(heartRate -> objectMapper.convertValue(heartRate, ResponseHeartRateDTO.class))
                    .toList();
        } else {
            throw new HeartRateNotFoundException();
        }
    }

    @Override
    public List<ResponseHeartRateDTO> getHeartRateOnLastMonthByHours(Authentication authentication, String startHours, String endHours) throws HtaException {
        Long userId = getCurrentUser(authentication);
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = startDate.minusMonths(1);
        List<HeartRate> heartRateByHoursLastMonth = IReportHeartRateRepository.getHeartRateOnLastWeekOrMonthByHours(userId,getSqlDateFromLocalDateTime(startDate), getSqlDateFromLocalDateTime(endDate), startHours, endHours);
        if(!heartRateByHoursLastMonth.isEmpty()) {
            return heartRateByHoursLastMonth.stream()
                    .map(heartRate -> objectMapper.convertValue(heartRate, ResponseHeartRateDTO.class))
                    .toList();
        } else {
            throw new HeartRateNotFoundException();
        }
    }

    @Override
    public ResponseHeartRateDTO getMaxHeartRateOnDay(Authentication authentication, Date date) throws HtaException{
        Long userId = getCurrentUser(authentication);
        if (!Objects.isNull(IReportHeartRateRepository.getMaxHeartRateOnDay(date, userId))) {
            return objectMapper.convertValue(IReportHeartRateRepository.getMaxHeartRateOnDay(date, userId), ResponseHeartRateDTO.class);
        }
        throw new HeartRateNotFoundException();
    }

    @Override
    public List<ResponseHeartRateDTO> getMaxHeartRateByDayLastWeek(Authentication authentication) throws HtaException {
        Long userId = getCurrentUser(authentication);
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime beforeDay = startDate;
        LocalDateTime endDate = startDate.minusWeeks(1);
        List<HeartRate> maxHeartRateByDayOneWeek = new ArrayList<>();
        while (!beforeDay.equals(endDate)) {
            HeartRate maxHeartRateOnDay = IReportHeartRateRepository.getMaxHeartRateOnDay(getSqlDateFromLocalDateTime(beforeDay), userId);
            if (!Objects.isNull(maxHeartRateOnDay)) {
                maxHeartRateByDayOneWeek.add(maxHeartRateOnDay);
            }
            beforeDay = beforeDay.minusDays(1);
        }
        if(!maxHeartRateByDayOneWeek.isEmpty()) {
            return maxHeartRateByDayOneWeek.stream()
                    .map(heartRate -> objectMapper.convertValue(heartRate, ResponseHeartRateDTO.class))
                    .toList();
        } else {
            throw new HeartRateNotFoundException();
        }
    }
}
