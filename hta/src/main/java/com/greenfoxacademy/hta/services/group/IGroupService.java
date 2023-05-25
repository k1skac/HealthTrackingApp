package com.greenfoxacademy.hta.services.group;

import com.greenfoxacademy.hta.dtos.groupdto.UserNameDTO;
import com.greenfoxacademy.hta.models.user.User;

import java.util.List;
import java.util.Optional;

public interface IGroupService {
    List<User> findNNearestUser(User user, int n);
    List<User> getUsersByScoreOriginal(User user, int n);
    List<User> getUsersByScore(User user, int n);
    List<User> getUsersByScoreAge(User user, int n);
    Optional<Integer> getAge(User user);
    List<UserNameDTO> getUserNames(List<User> users);
}
