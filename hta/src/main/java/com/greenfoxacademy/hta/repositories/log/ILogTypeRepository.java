package com.greenfoxacademy.hta.repositories.log;

import com.greenfoxacademy.hta.models.log.LogType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ILogTypeRepository extends JpaRepository<LogType, Integer> {
    LogType findLogTypeByName(String name);
}
