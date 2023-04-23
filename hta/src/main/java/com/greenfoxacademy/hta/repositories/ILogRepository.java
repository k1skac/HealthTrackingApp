package com.greenfoxacademy.hta.repositories;

import com.greenfoxacademy.hta.models.Log;
import com.greenfoxacademy.hta.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ILogRepository  extends JpaRepository<Log,Integer> {

  /*@Query("SELECT log FROM Log log WHERE log.logCreatedAt >= ?1" +
          "AND log.logCreatedAt <= ?2")*/
  List<Log> findAllByLogCreatedAtBetween(LocalDateTime start, LocalDateTime end);
  List<Log> findAllByUserAndLogCreatedAtBetween(User user, LocalDateTime start, LocalDateTime end);

}
