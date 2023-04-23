package com.greenfoxacademy.hta.repositories;

import com.greenfoxacademy.hta.models.LogType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ILogTypeRepository extends JpaRepository<LogType,Integer> {

  public LogType findLogTypeByName(String name);

}
