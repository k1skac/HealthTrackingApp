package com.greenfoxacademy.hta.services.user;

import com.greenfoxacademy.hta.dtos.LoginDto;
import com.greenfoxacademy.hta.dtos.RegisterDto;
import com.greenfoxacademy.hta.models.roles.Role;
import com.greenfoxacademy.hta.models.User;
import org.springframework.http.ResponseEntity;


public interface IUserService {
   String authenticate(LoginDto loginDto);
   ResponseEntity<?> register (RegisterDto registerDto);
   Role saveRole(Role role);
   User saveUser(User user) ;
   User findByEmail(String email);
}
