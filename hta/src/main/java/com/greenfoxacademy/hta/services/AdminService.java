package com.greenfoxacademy.hta.services;

import com.greenfoxacademy.hta.dtos.DeleteDTO;
import com.greenfoxacademy.hta.dtos.LogDTO;
import com.greenfoxacademy.hta.dtos.LoginDto;
import com.greenfoxacademy.hta.exceptions.UserNotFoundException;
import com.greenfoxacademy.hta.models.Log;
import com.greenfoxacademy.hta.models.User;
import com.greenfoxacademy.hta.repositories.ILogRepository;
import com.greenfoxacademy.hta.repositories.ILogTypeRepository;
import com.greenfoxacademy.hta.repositories.IUserRepository;
import com.greenfoxacademy.hta.services.user.IUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class AdminService implements IAdminService {

  private final IUserRepository iUserRepository ;
  private final ILogRepository iLogRepository ;
  private final ILogTypeRepository iLogTypeRepository ;
  private final PasswordEncoder passwordEncoder ;
  private final IUserService iUserService ;


  public ResponseEntity<?> adminUserChangePassword(LoginDto loginDto) throws UserNotFoundException {
    User user = iUserRepository.findByEmail(loginDto.getEmail()).orElseThrow(UserNotFoundException::new);
    user.setPassword(passwordEncoder.encode(loginDto.getPassword()));
    iUserRepository.save(user);
    iUserService.newLog(iLogTypeRepository.findLogTypeByName("adminpwchange"), user,
            "The password of "+ loginDto.getEmail() + " has changed");
    return new ResponseEntity<>("User password of "+user.getUsername()+ " is changed by admin", HttpStatus.OK);
  }

  public ResponseEntity<?> adminLog(LogDTO logDto) throws UserNotFoundException {
    if (logDto.getEmail().equals("ALL")||logDto.getEmail().equals("all")) {
      List<Log> logList =iLogRepository.findAllByLogCreatedAtBetween(logDto.getPeriodStartTime(),
               logDto.getPeriodEndTime());
      return new ResponseEntity<>(logList, HttpStatus.OK);
    } else {
      if (iUserRepository.existsByEmail(logDto.getEmail())) {
        return  new ResponseEntity<>(iLogRepository.findAllByUserAndLogCreatedAtBetween(iUserRepository.findByEmail(logDto.getEmail())
                .orElseThrow(UserNotFoundException::new), logDto.getPeriodStartTime(), logDto.getPeriodEndTime()), HttpStatus.OK);
      } else {
        return new ResponseEntity<>("The email is not registered", HttpStatus.BAD_REQUEST);
      }

    }
  }

  public ResponseEntity<?> userDelete(DeleteDTO deleteDTO) throws UserNotFoundException {
    User user = iUserRepository.findByEmail(deleteDTO.getEmail()).orElseThrow(UserNotFoundException::new);
    if (user.isActive()) {
      user.setActive(false);
      iUserService.newLog(iLogTypeRepository.findLogTypeByName("adminuserdelete"), user,"The account of  "+ deleteDTO.getEmail() + " has deactivated by admin");
      return new ResponseEntity<>("The account of " +deleteDTO.getEmail()+ " has deleted." , HttpStatus.OK);
    } else {
      return new ResponseEntity<>("The account of " +deleteDTO.getEmail()+ " has already deactivated, nothing happened now." , HttpStatus.NOT_MODIFIED);
    }
  }
}
