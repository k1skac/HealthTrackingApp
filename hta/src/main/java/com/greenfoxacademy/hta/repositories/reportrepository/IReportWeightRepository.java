package com.greenfoxacademy.hta.repositories.reportrepository;


import com.greenfoxacademy.hta.models.user.Weight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface IReportWeightRepository extends JpaRepository<Weight, Long> {
    @Query("SELECT weight FROM Weight weight WHERE weight.user.id = ?1 ORDER BY weight.weightMeasuredAt DESC LIMIT 1")
    Weight getCurrentWeight(Long userId);

    @Query("SELECT weight FROM Weight weight WHERE weight.user.id = ?3 AND date(weight.weightMeasuredAt) BETWEEN ?1 And ?2")
    List<Weight> getWeightInPeriod(Date startDate, Date endDate, Long userId);
    //-----------------------


    @Query("SELECT max(weight.weight) FROM Weight weight WHERE weight.user.id = ?3 AND weight.weightMeasuredAt BETWEEN ?1 And ?2")
    Weight findMaxWeightInPeriod(Date startDate, Date endDate, Long userId);

    @Query("SELECT min(weight.weight) FROM Weight weight WHERE weight.user.id = ?3 AND weight.weightMeasuredAt BETWEEN ?1 And ?2")
    Weight findMinWeightInPeriod(Date startDate, Date endDate, Long userId);

    @Query("SELECT avg (weight.weight) FROM Weight weight WHERE weight.user.id = ?3 AND weight.weightMeasuredAt BETWEEN ?1 And ?2")
    Weight getAverageWeightInPeriod(Date startDate, Date endDate, Long userId);


    @Query("SELECT weight FROM Weight weight WHERE weight.user.id =?1 AND weight.weightMeasuredAt > ?2")
    List<Weight> getWeightsAfterLimit(Long userId, Date deadLineOfLastCalorieLimit);

}
