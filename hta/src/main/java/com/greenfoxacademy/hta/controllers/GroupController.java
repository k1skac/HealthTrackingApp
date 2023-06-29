package com.greenfoxacademy.hta.controllers;
import com.greenfoxacademy.hta.dtos.groupdto.UserNameDTO;
import com.greenfoxacademy.hta.models.user.User;
import com.greenfoxacademy.hta.services.group.IGroupService;
import com.greenfoxacademy.hta.services.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/get-group")
public class GroupController {
    private final IUserService userService;
    private final IGroupService groupService;
    @GetMapping("/nearest")
    public ResponseEntity<?>getNNearest(@RequestBody int nNearestUsers, Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());
        List<User> list = groupService.findNNearestUser(user, nNearestUsers);
        List<UserNameDTO> dtoList = groupService.getUserNames(list);
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/highest-score")
    public ResponseEntity<?>getHighestScore(@RequestBody int nUsersByScore, Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());
        List<User> list = groupService.getUsersByScore(user, nUsersByScore);
        List<UserNameDTO> dtoList = groupService.getUserNames(list);
        return ResponseEntity.ok(dtoList);
    }
}