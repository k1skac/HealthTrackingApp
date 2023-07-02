package com.greenfoxacademy.hta.services.user;

import com.greenfoxacademy.hta.dtos.*;
import com.greenfoxacademy.hta.exceptions.CityNotFoundException;
import com.greenfoxacademy.hta.exceptions.UserEmailAlreadyTakenException;
import com.greenfoxacademy.hta.exceptions.UserEmailMissingException;
import com.greenfoxacademy.hta.exceptions.UserNotFoundException;
import com.greenfoxacademy.hta.models.log.LogType;
import com.greenfoxacademy.hta.models.roles.Role;
import com.greenfoxacademy.hta.models.user.User;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface IUserService {
    String authenticate(LoginDTO loginDto) throws UserNotFoundException;
    String register(RegisterDTO registerDTO) throws UserEmailAlreadyTakenException, UserEmailMissingException;
    Role saveRole(Role role);
    String userChangePassword(String newPassword, Authentication authentication) throws UserNotFoundException;
    void newLog(LogType logType, User user, String description);
    User saveUser(User user);
    User findByEmail(String email);
    String updateUserProfile(UpdateProfileDTO updateProfileDTO, Authentication authentication) throws CityNotFoundException;

    UpdateProfileDTO getUserProfileData(Authentication authentication);
    List<String> getCityNameList();
    boolean emailExist(String email);



}
