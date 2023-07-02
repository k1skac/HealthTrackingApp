package com.greenfoxacademy.hta.services.user;

import com.greenfoxacademy.hta.dtos.*;
import com.greenfoxacademy.hta.exceptions.CityNotFoundException;
import com.greenfoxacademy.hta.exceptions.UserEmailAlreadyTakenException;
import com.greenfoxacademy.hta.exceptions.UserEmailMissingException;
import com.greenfoxacademy.hta.exceptions.UserNotFoundException;
import com.greenfoxacademy.hta.models.log.Log;
import com.greenfoxacademy.hta.models.log.LogType;
import com.greenfoxacademy.hta.models.user.BiologicalGender;
import com.greenfoxacademy.hta.models.user.City;
import com.greenfoxacademy.hta.models.user.User;
import com.greenfoxacademy.hta.repositories.ICityRepository;
import com.greenfoxacademy.hta.repositories.log.ILogRepository;
import com.greenfoxacademy.hta.repositories.log.ILogTypeRepository;
import com.greenfoxacademy.hta.models.roles.Role;
import com.greenfoxacademy.hta.models.roles.RoleName;
import com.greenfoxacademy.hta.repositories.IRoleRepository;
import com.greenfoxacademy.hta.repositories.IUserRepository;
import com.greenfoxacademy.hta.security.JwtUtilities;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final AuthenticationManager authenticationManager;
    private final IUserRepository iUserRepository;
    private final IRoleRepository iRoleRepository;
    private final ILogRepository iLogRepository;
    private final ILogTypeRepository iLogTypeRepository;
    private final ICityRepository iCityRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtilities jwtUtilities;

    @Override
    public Role saveRole(Role role) {
        return iRoleRepository.save(role);
    }

    @Override
    public User saveUser(User user) {
        return iUserRepository.save(user);
    }

    @Override
    public String register(RegisterDTO registerDTO) throws UserEmailAlreadyTakenException, UserEmailMissingException {
        if (registerDTO.getEmail()==null||registerDTO.getEmail().equals("")) {
            throw new UserEmailMissingException();
        } else{
            if(iUserRepository.existsByEmail(registerDTO.getEmail()))
            { throw new UserEmailAlreadyTakenException();
            }
            else {
                User user = new User();
                user.setEmail(registerDTO.getEmail());
                user.setUsername(registerDTO.getUsername());
                user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
                Role role = iRoleRepository.findByRoleName(RoleName.USER);
                user.setRoles(Collections.singletonList(role));
                user.setRealName(registerDTO.getRealName());
                if (registerDTO.getBiologicalGender().equals("MALE")) {
                    user.setBiologicalGender(BiologicalGender.MALE);
                } else if (registerDTO.getBiologicalGender().equals("FEMALE")) {
                    user.setBiologicalGender(BiologicalGender.MALE);
                } else {
                    user.setBiologicalGender(BiologicalGender.UNDEFINED);
                }
                user.setHeight(registerDTO.getHeight());
                user.setBirthDate(registerDTO.getBirthDate());
                user.setActive(true);
                iUserRepository.save(user);
                newLog(iLogTypeRepository.findLogTypeByName("registration"), iUserRepository.findByEmail(registerDTO.getEmail()).orElseThrow(), "");
                String token = jwtUtilities.generateToken(registerDTO.getEmail(), Collections.singletonList(role.getRoleName()));
                return jwtUtilities.generateToken(user.getUsername(), user.getRoles().stream().map(Role::getRoleName).toList());
            }
        }
    }

    public UpdateProfileDTO getUserProfileData(Authentication authentication) {
        UpdateProfileDTO userRequestData = new UpdateProfileDTO();
        User user = iUserRepository.findByEmail(authentication.getName()).orElseThrow();
        userRequestData.setUsername(user.getUsername());
        userRequestData.setRealName(user.getRealName());
        if (user.getBiologicalGender().equals(BiologicalGender.FEMALE)) {
            userRequestData.setBiologicalGender("Female");
        } else if (user.getBiologicalGender().equals(BiologicalGender.MALE)) {
            userRequestData.setBiologicalGender("Male");
        } else {
            userRequestData.setBiologicalGender("Undefined");
        }
        userRequestData.setBirthDate(user.getBirthDate());
        userRequestData.setHeight(user.getHeight());
        if (!Objects.isNull(user.getCity())) {
            userRequestData.setCityName(user.getCity().getCityName());
        } else {
            userRequestData.setCityName("no defined");
        }
        return userRequestData;
    }

    public String updateUserProfile(UpdateProfileDTO updateProfileDTO, Authentication authentication) throws CityNotFoundException {
        User user = iUserRepository.findByEmail(authentication.getName()).orElseThrow();
        try {
            user.setUsername(updateProfileDTO.getUsername());
            user.setRealName(updateProfileDTO.getRealName());
            if (updateProfileDTO.getBiologicalGender().equalsIgnoreCase("MALE")) {
                user.setBiologicalGender(BiologicalGender.MALE);
            } else if (updateProfileDTO.getBiologicalGender().equalsIgnoreCase("FEMALE")) {
                user.setBiologicalGender(BiologicalGender.FEMALE);
            } else {
                user.setBiologicalGender(BiologicalGender.UNDEFINED);
            }
            user.setHeight(updateProfileDTO.getHeight());
            user.setBirthDate(updateProfileDTO.getBirthDate());
            if (!(updateProfileDTO.getCityName().isEmpty())) {
                if (iCityRepository.existsByCityName(updateProfileDTO.getCityName())) {
                    user.setCity(iCityRepository.findByCityName(updateProfileDTO.getCityName()));
                } else {
                    throw new CityNotFoundException(updateProfileDTO.getCityName());
                }
            }
            iUserRepository.save(user);
            newLog(iLogTypeRepository.findLogTypeByName("registration"), iUserRepository.findByEmail(authentication.getName()).orElseThrow(), "");
            return "The profile of "+ user.getEmail()+" has changed";
        }
        catch (CityNotFoundException e) {
            throw new CityNotFoundException(updateProfileDTO.getCityName());
        }
    }

    public List<String> getCityNameList() {
        List<City> cityList = iCityRepository.findAll();
        List<String> cityNameList = new ArrayList<>();
        for (City city : cityList) {
            String cityName = city.getCityName();
            cityNameList.add(cityName);
        }
        return cityNameList;
    }

    @Override
    public User findByEmail(String email) {
        return iUserRepository.findByEmail(email).orElse(null);
    }

    @Override
    public String authenticate(LoginDTO loginDTO) throws UserNotFoundException {
        Authentication authentication= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getEmail(),
                        loginDTO.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = iUserRepository.findByEmail(authentication.getName()).orElseThrow(UserNotFoundException::new);
        if (user.isActive()) {
            List<String> rolesNames = new ArrayList<>();
            user.getRoles().forEach(r -> rolesNames.add(r.getRoleName()));
            newLog(iLogTypeRepository.findLogTypeByName("login"), user, "");
            return jwtUtilities.generateToken(user.getUsername(), rolesNames);
        } else {
            return "The user " + loginDTO.getEmail() + " is not active member.";
        }
    }

    public String userChangePassword(String newPassword, Authentication authentication) throws UserNotFoundException{
        User user = iUserRepository.findByEmail(authentication.getName()).orElseThrow(UserNotFoundException::new);
        user.setPassword(passwordEncoder.encode(newPassword));
        iUserRepository.save(user);
        newLog(iLogTypeRepository.findLogTypeByName("pwchange"), user,"The password of "+ user.getEmail()+" has changed by user");
        return "The password of "+ user.getEmail()+" has changed";
    }

    public void newLog(LogType logType, User user, String description) {
        iLogRepository.save(new Log(user, logType, description));
    }
}