package com.greenfoxacademy.hta.services;

import com.greenfoxacademy.hta.dtos.LoginDto;
import com.greenfoxacademy.hta.dtos.RegisterDto;
import com.greenfoxacademy.hta.models.Role;
import com.greenfoxacademy.hta.models.User;
import org.springframework.http.ResponseEntity;


public interface IUserService {
   //ResponseEntity<?> register (RegisterDto registerDto);
 //  ResponseEntity<BearerToken> authenticate(LoginDto loginDto);

   String authenticate(LoginDto loginDto);
   ResponseEntity<?> register (RegisterDto registerDto);
   Role saveRole(Role role);
   User saverUser (User user) ;
}
