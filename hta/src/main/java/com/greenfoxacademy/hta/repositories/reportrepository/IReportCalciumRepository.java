package com.greenfoxacademy.hta.repositories.reportrepository;

import com.greenfoxacademy.hta.models.bloodlabdata.Calcium;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IReportCalciumRepository extends JpaRepository<Calcium, Long> {

    @Query("Select calcium FROM Calcium calcium where calcium.bloodLabData.id = ?1 ORDER BY calcium.measureTime DESC  limit 1")
    Calcium getLastCalciumData(Long laborId);

    List<Calcium> findAllByBloodLabDataId(Long laborId);

    @Query("SELECT calcium FROM Calcium calcium WHERE calcium.bloodLabData.id = ?1 AND calcium.measureTime BETWEEN ?2 AND ?3")
    List<Calcium> findAllCalciumLaborInPeriod(Long laborId, LocalDateTime startDay, LocalDateTime endDay);

    @Query("SELECT count(calcium.id) FROM Calcium  calcium WHERE calcium.bloodLabData.id = ?1")
    Long countCalciumById(Long laborId);
}
