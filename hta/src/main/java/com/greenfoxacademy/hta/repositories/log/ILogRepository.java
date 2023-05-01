package com.greenfoxacademy.hta.repositories.log;

import com.greenfoxacademy.hta.models.log.Log;
import com.greenfoxacademy.hta.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ILogRepository extends JpaRepository<Log, Integer> {
    List<Log> findAllByLogCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    List<Log> findAllByUserAndLogCreatedAtBetween(User user, LocalDateTime start, LocalDateTime end);
}
