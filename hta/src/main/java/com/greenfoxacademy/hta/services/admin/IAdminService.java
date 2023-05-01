package com.greenfoxacademy.hta.services.admin;

import com.greenfoxacademy.hta.dtos.DeleteDTO;
import com.greenfoxacademy.hta.dtos.LogDTO;
import com.greenfoxacademy.hta.dtos.LoginDTO;
import com.greenfoxacademy.hta.dtos.LogResponseDTO;
import com.greenfoxacademy.hta.exceptions.HtaException;
import com.greenfoxacademy.hta.exceptions.UserNotFoundException;
import org.springframework.http.ResponseEntity;

public interface IAdminService {
    String adminUserChangePassword(LoginDTO loginDto) throws UserNotFoundException;

    LogResponseDTO adminLog(LogDTO logDto) throws UserNotFoundException;

    String userDelete(DeleteDTO deleteDTO) throws UserNotFoundException, HtaException;

}
