package com.greenfoxacademy.hta.repositories.reportrepository;


import com.greenfoxacademy.hta.models.user.HeartRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface IReportHeartRateRepository extends JpaRepository<HeartRate, Long> {

    @Query("SELECT heartRate FROM HeartRate heartRate WHERE heartRate.user.id = ?2 and date(heartRate.heartRateMeasuredAt) = ?1 order by heartRate.heartRate desc LIMIT 1")
    HeartRate getMaxHeartRateOnDay(Date date, Long userId);
    @Query("SELECT heartRate FROM HeartRate heartRate WHERE heartRate.user.id = ?1 and heartRate.heartRateMeasuredAt between ?2 And ?3")
    List<HeartRate> getHeartRateOnLastWeek(Long userId, Date startDate, Date endDate);

    @Query("SELECT heartRate FROM HeartRate heartRate WHERE heartRate.user.id = ?1 AND date(heartRate.heartRateMeasuredAt) = ?2")
    List<HeartRate> getHeartRateOnDay(Long userId, Date convertedDate);

    @Query("select heartRate FROM HeartRate heartRate WHERE heartRate.user.id = ?1 and date(heartRate.heartRateMeasuredAt) BETWEEN ?2 and ?3")
    List<HeartRate> getHeartRateOnPeriod(Long userId, Date startDate, Date endDate);

    @Query("select  heartRate From HeartRate heartRate where heartRate.user.id = ?4 " +
            "and date(heartRate.heartRateMeasuredAt) = ?1 " +
            "and HOUR(heartRate.heartRateMeasuredAt) BETWEEN ?2 AND  ?3")
    List<HeartRate> getHeartRateOneDayByHours(Date date, String startHours, String endHours, Long userId);

    @Query("select  heartRate From HeartRate heartRate where heartRate.user.id = ?1 " +
            "and date(heartRate.heartRateMeasuredAt) BETWEEN ?2 AND  ?3" +
            "and HOUR(heartRate.heartRateMeasuredAt) BETWEEN ?4 AND  ?5")
    List<HeartRate> getHeartRateOnLastWeekOrMonthByHours(Long userId, Date startDate, Date endDate, String startHours, String endHours);

    @Query("select  heartRate From HeartRate heartRate where heartRate.user.id = ?1 " +
            "and date(heartRate.heartRateMeasuredAt) BETWEEN ?2 AND  ?3" +
            "and HOUR(heartRate.heartRateMeasuredAt) BETWEEN ?4 AND  ?5")
    List<HeartRate> getHeartRateOnPeriodByHours(Long userId, Date startDate, Date endDate, String startHours, String endHours);
}
