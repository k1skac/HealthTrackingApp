package com.greenfoxacademy.hta.repositories.reportrepository;

import com.greenfoxacademy.hta.models.bloodlabdata.Glucose;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IReportGlucoseRepository extends JpaRepository<Glucose, Long> {

    @Query("Select glucose FROM Glucose glucose where glucose.bloodLabData.id = ?1 ORDER BY glucose.measureTime DESC  limit 1")
    Glucose getLastGlucoseData(Long laborId);

    List<Glucose> findAllByBloodLabDataId(Long laborId);

    @Query("SELECT glucose FROM Glucose glucose WHERE glucose.bloodLabData.id = ?1 AND glucose.measureTime BETWEEN ?2 AND ?3")
    List<Glucose> findAllGlucoseLaborInPeriod(Long laborId, LocalDateTime startDay, LocalDateTime endDay);

    @Query("SELECT count(glucose.id) FROM Glucose glucose WHERE glucose.bloodLabData.id = ?1")
    Long countGlucoseById(Long laborId);
}
