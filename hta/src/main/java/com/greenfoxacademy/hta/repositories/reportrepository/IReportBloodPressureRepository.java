package com.greenfoxacademy.hta.repositories.reportrepository;

import com.greenfoxacademy.hta.models.user.BloodPressure;
import com.greenfoxacademy.hta.models.user.Weight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface IReportBloodPressureRepository extends JpaRepository<BloodPressure, Long> {
    @Query("SELECT bloodPressure FROM BloodPressure  bloodPressure WHERE bloodPressure.user.id = ?2 and date(bloodPressure.bloodPressureMeasuredAt) = ?1 order by bloodPressure.systolic desc LIMIT 1")
    BloodPressure getMaxSystolicOnDay(Date date, Long userId);
    @Query("SELECT bloodPressure FROM BloodPressure bloodPressure WHERE bloodPressure.user.id = ?1 and bloodPressure.bloodPressureMeasuredAt between ?2 And ?3")
    List<BloodPressure> getBloodPressureOnLastWeek(Long userId, Date startDate, Date endDate);

    @Query("SELECT bloodPressure FROM BloodPressure bloodPressure WHERE bloodPressure.user.id = ?1 AND date(bloodPressure.bloodPressureMeasuredAt) = ?2")
    List<BloodPressure> getBloodPressureOnDay(Long userId, Date convertedDate);

    @Query("select bloodPressure FROM BloodPressure bloodPressure WHERE bloodPressure.user.id = ?1 and date(bloodPressure.bloodPressureMeasuredAt) BETWEEN ?2 and ?3")
    List<BloodPressure> getBloodPressureOnPeriod(Long userId, Date startDate, Date endDate);

    @Query("select  bloodPressure From BloodPressure bloodPressure where bloodPressure.user.id = ?4 " +
            "and date(bloodPressure.bloodPressureMeasuredAt) = ?1 " +
            "and HOUR(bloodPressure.bloodPressureMeasuredAt) BETWEEN ?2 AND  ?3")
    List<BloodPressure> getBloodPressureOneDayByHours(Date date, String startHours, String endHours, Long userId);

    @Query("select  bloodPressure From BloodPressure bloodPressure where bloodPressure.user.id = ?1 " +
            "and date(bloodPressure.bloodPressureMeasuredAt) BETWEEN ?2 AND  ?3" +
            "and HOUR(bloodPressure.bloodPressureMeasuredAt) BETWEEN ?4 AND  ?5")
    List<BloodPressure> getBloodPressureOnLastWeekOrMonthByHours(Long userId, Date startDate, Date endDate, String startHours, String endHours);

    @Query("select  bloodPressure From BloodPressure bloodPressure where bloodPressure.user.id = ?1 " +
            "and date(bloodPressure.bloodPressureMeasuredAt) BETWEEN ?2 AND  ?3" +
            "and HOUR(bloodPressure.bloodPressureMeasuredAt) BETWEEN ?4 AND  ?5")
    List<BloodPressure> getBloodPressureOnPeriodByHours(Long userId, Date startDate, Date endDate, String startHours, String endHours);

    List<BloodPressure> findTop7ByUserIdOrderByBloodPressureMeasuredAt(Long UserId);
}
