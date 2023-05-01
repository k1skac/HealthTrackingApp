package com.greenfoxacademy.hta.services.user;

import com.greenfoxacademy.hta.dtos.BearerToken;
import com.greenfoxacademy.hta.dtos.LoginDTO;
import com.greenfoxacademy.hta.dtos.RegisterDTO;
import com.greenfoxacademy.hta.exceptions.UserEmailAlreadyTakenException;
import com.greenfoxacademy.hta.exceptions.UserEmailMissingException;
import com.greenfoxacademy.hta.exceptions.UserNotFoundException;
import com.greenfoxacademy.hta.models.log.LogType;
import com.greenfoxacademy.hta.models.roles.Role;
import com.greenfoxacademy.hta.models.user.User;
import org.springframework.security.core.Authentication;

public interface IUserService {
    String authenticate(LoginDTO loginDto) throws UserNotFoundException;
    BearerToken register(RegisterDTO registerDTO) throws UserEmailAlreadyTakenException, UserEmailMissingException;
    Role saveRole(Role role);
    String userChangePassword(String newPassword, Authentication authentication) throws UserNotFoundException;
    void newLog(LogType logType, User user, String description);
    User saveUser(User user);
    User findByEmail(String email);
}
