package com.greenfoxacademy.hta.repositories.reportrepository;


import com.greenfoxacademy.hta.models.bloodlabdata.TriGlycerides;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IReportTriGlyceridesRepository extends JpaRepository<TriGlycerides, Long> {

    @Query("Select triGlycerides FROM TriGlycerides triGlycerides where triGlycerides.bloodLabData.id = ?1 ORDER BY triGlycerides.measureTime DESC  limit 1")
    TriGlycerides getLastTriGlyceridesData(Long laborId);

    List<TriGlycerides> findAllByBloodLabDataId(Long laborId);

    @Query("SELECT triGlycerides FROM TriGlycerides triGlycerides WHERE triGlycerides.bloodLabData.id = ?1 AND triGlycerides.measureTime BETWEEN ?2 AND ?3")
    List<TriGlycerides> findAllTriGlyceridesLaborInPeriod(Long laborId, LocalDateTime startDay, LocalDateTime endDay);

    @Query("SELECT count(triGlycerides.id) FROM TriGlycerides  triGlycerides WHERE triGlycerides.bloodLabData.id = ?1")
    Long countTriGlyceridesById(Long laborId);
}
