package com.greenfoxacademy.hta.repositories.reportrepository;

import com.greenfoxacademy.hta.models.bloodlabdata.BloodLabData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface IReportBloodLabDataRepository extends JpaRepository<BloodLabData, Long> {

    @Query("select bloodlabdata.id FROM BloodLabData  bloodlabdata where bloodlabdata.user.id = ?1")
    Long getBloodLabDataId(Long userId);
}
