package com.greenfoxacademy.hta.services.admin;

import com.greenfoxacademy.hta.dtos.LogDTO;
import com.greenfoxacademy.hta.dtos.LoginDTO;
import com.greenfoxacademy.hta.dtos.LogResponseDTO;
import com.greenfoxacademy.hta.dtos.DeleteDTO;
import com.greenfoxacademy.hta.exceptions.HtaException;
import com.greenfoxacademy.hta.exceptions.UserNotActiveForDeleteException;
import com.greenfoxacademy.hta.exceptions.UserNotFoundException;
import com.greenfoxacademy.hta.models.user.User;
import com.greenfoxacademy.hta.repositories.log.ILogRepository;
import com.greenfoxacademy.hta.repositories.log.ILogTypeRepository;
import com.greenfoxacademy.hta.repositories.IUserRepository;
import com.greenfoxacademy.hta.services.user.IUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminService implements IAdminService {
    private final IUserRepository iUserRepository;
    private final ILogRepository iLogRepository;
    private final ILogTypeRepository iLogTypeRepository;
    private final PasswordEncoder passwordEncoder;
    private final IUserService iUserService;

    public String adminUserChangePassword(LoginDTO loginDto) throws UserNotFoundException {
        User user = iUserRepository.findByEmail(loginDto.getEmail()).orElseThrow(UserNotFoundException::new);
        user.setPassword(passwordEncoder.encode(loginDto.getPassword()));
        iUserRepository.save(user);
        iUserService.newLog(iLogTypeRepository.findLogTypeByName("adminpwchange"), user,
                "The password of "+ loginDto.getEmail() + " has changed");
        return "User password of "+ user.getUsername() + " is changed by admin";
    }

    public LogResponseDTO adminLog(LogDTO logDto) throws UserNotFoundException {
        if (logDto.getEmail().equalsIgnoreCase("ALL")) {
            return new LogResponseDTO(iLogRepository.findAllByLogCreatedAtBetween(logDto.getPeriodStartTime(),
                    logDto.getPeriodEndTime()));
        } else {
            if (iUserRepository.existsByEmail(logDto.getEmail())) {
                return new LogResponseDTO(iLogRepository.findAllByUserAndLogCreatedAtBetween(iUserRepository.findByEmail(logDto.getEmail())
                        .orElseThrow(UserNotFoundException::new), logDto.getPeriodStartTime(), logDto.getPeriodEndTime()));
            } else {
                throw new UserNotFoundException();
            }
        }
    }

    public String userDelete(DeleteDTO deleteDTO) throws HtaException {
        User user = iUserRepository.findByEmail(deleteDTO.getEmail()).orElseThrow(UserNotFoundException::new);
        if (user.isActive()) {
            user.setActive(false);
            iUserService.newLog(iLogTypeRepository.findLogTypeByName("adminuserdelete"), user,"The account of  " + deleteDTO.getEmail() + " has deactivated by admin");
            return "The account of " + deleteDTO.getEmail() + " has deleted.";
        } else {
            throw new UserNotActiveForDeleteException(user.getEmail());
        }
    }
}