package com.greenfoxacademy.hta.services;

import com.greenfoxacademy.hta.dtos.DeleteDTO;
import com.greenfoxacademy.hta.dtos.LogDTO;
import com.greenfoxacademy.hta.dtos.LoginDto;
import com.greenfoxacademy.hta.exceptions.UserNotFoundException;
import org.springframework.http.ResponseEntity;

public interface IAdminService {

  ResponseEntity<?> adminUserChangePassword(LoginDto loginDto) throws UserNotFoundException;

  ResponseEntity<?> adminLog(LogDTO logDto) throws UserNotFoundException;

  ResponseEntity<?> userDelete(DeleteDTO deleteDTO) throws UserNotFoundException;

}
