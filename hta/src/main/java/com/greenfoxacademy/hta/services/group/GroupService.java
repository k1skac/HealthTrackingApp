package com.greenfoxacademy.hta.services.group;

import com.greenfoxacademy.hta.dtos.groupdto.UserNameDTO;
import com.greenfoxacademy.hta.models.goalentities.Goal;
import com.greenfoxacademy.hta.models.user.BNOCode;
import com.greenfoxacademy.hta.models.user.User;
import com.greenfoxacademy.hta.models.user.Weight;
import com.greenfoxacademy.hta.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;

@Service
public class GroupService implements IGroupService{
    private final IUserRepository userRepository;
    @Autowired
    public GroupService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findNNearestUser(User user, int n) {
        List<User> allUsers = userRepository.findAll();
        allUsers.remove(user);
        List<User> NNearestUsers = new ArrayList<>();
        Map<User, Double> userMap = new HashMap<>();
        for (User otherUser : allUsers) {
            if (otherUser.getCity() != null) {
                double x = otherUser.getCity().getXLongitude() - user.getCity().getXLongitude();
                double y = otherUser.getCity().getYLatitude() - user.getCity().getYLatitude();
                Double distance = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
                userMap.put(otherUser, distance);
            }
        }
        List<Map.Entry<User, Double>> sortedUserList = new ArrayList<>(userMap.entrySet());
        sortedUserList.sort(Map.Entry.comparingByValue());
        for (int i = 0; i < n && i < sortedUserList.size(); i++) {
            NNearestUsers.add(sortedUserList.get(i).getKey());
        }
        return NNearestUsers;
    }

    @Override
    public List<User> getUsersByScoreOriginal(User user, int n) {
        return null;
    }

    public Optional<Goal> getMostRecentGoal(User user) {
        Goal mostRecentGoal = null;
        for (Goal goal : user.getGoals()) {
            if (mostRecentGoal == null || goal.getCreationDate().compareTo(mostRecentGoal.getCreationDate()) > 0) {
                mostRecentGoal = goal;
            }
        }
        return Optional.ofNullable(mostRecentGoal);
    }

    @Override
    public List<User> getUsersByScore(User user, int n){
        List<User> allUsers = userRepository.findAll();
        allUsers.remove(user);
        List<BNOCode> userIllnessCodes = user.getIllnessCodes();
        Optional<Integer> userAge = getAge(user);
        Optional<Goal> userGoal = getMostRecentGoal(user);
        for (int i = 0; i < allUsers.size(); i++) {
            Optional<Goal> otherUserGoal = getMostRecentGoal(allUsers.get(i));
            if (userGoal.isPresent() && otherUserGoal.isPresent() &&
                    Math.abs(userGoal.get().getCalorieIntakeLimit().getCalorieLimit() - otherUserGoal.get().getCalorieIntakeLimit().getCalorieLimit()) < 300) {
                allUsers.get(i).setUserScore(allUsers.get(i).getUserScore() + 1);
            }

            if (user.getWeights() != null && allUsers.get(i).getWeights() != null) {
                Optional<Weight> userWeight = user.getWeights().stream().reduce((first, second) -> second);
                Optional<Weight> otherUserWeight = allUsers.get(i).getWeights().stream().reduce((first, second) -> second);
                if (userWeight.isPresent() && otherUserWeight.isPresent() &&
                        Math.abs(userWeight.get().getWeight() - otherUserWeight.get().getWeight()) < 10) {
                    allUsers.get(i).setUserScore(allUsers.get(i).getUserScore() + 1);
                }
            }

            Optional<Integer> otherUserAge = getAge(allUsers.get(i));
            if (userAge.isPresent() && otherUserAge.isPresent() && Math.abs(userAge.get() - otherUserAge.get()) < 5) {
                allUsers.get(i).setUserScore(allUsers.get(i).getUserScore() + 2);
            }

            List<BNOCode> iIllnessCodes = allUsers.get(i).getIllnessCodes();
            if (iIllnessCodes != null) {
                List<BNOCode> commonIllnessCodes = new ArrayList<>(userIllnessCodes);
                commonIllnessCodes.retainAll(iIllnessCodes);
                if (!commonIllnessCodes.isEmpty()) {
                    allUsers.get(i).setUserScore(allUsers.get(i).getUserScore() + 5);
                }
            }
        }
        allUsers.sort(Comparator.comparingInt(User::getUserScore).reversed());
        List<User> resultList = allUsers.subList(0, Math.min(n, allUsers.size()));
        allUsers.forEach(u -> u.setUserScore(0));
        return resultList;
    }

    @Override
    public List<User> getUsersByScoreAge(User user, int n) {
        List<User> allUsers = userRepository.findAll();
        allUsers.remove(user);
        Optional<Integer> userAge = getAge(user);
        for (int i = 0; i < allUsers.size(); i++) {
            Optional<Integer> otherUserAge = getAge(allUsers.get(i));
            if (userAge.isPresent() && otherUserAge.isPresent() && Math.abs(userAge.get() - otherUserAge.get()) < 5) {
                allUsers.get(i).setUserScore(allUsers.get(i).getUserScore() + 2);
            }
        }
        allUsers.sort(Comparator.comparingInt(User::getUserScore).reversed());
        List<User> resultList = allUsers.subList(0, Math.min(n, allUsers.size()));
        allUsers.forEach(u -> u.setUserScore(0));
        return resultList;
    }
    @Override
    public Optional<Integer> getAge(User user) {
        if (user.getBirthDate() != null) {
            LocalDate currentDate = LocalDate.now();
            return Optional.of(Period.between(user.getBirthDate(), currentDate).getYears());
        }
        else return Optional.empty();
    }
    @Override
    public List<UserNameDTO> getUserNames(List<User> users) {
        List<UserNameDTO> userNames = new ArrayList<>();
        for (User user : users) {
            UserNameDTO dto = new UserNameDTO();
            dto.setDtoName(user.getRealName());
            userNames.add(dto);
        }
        return userNames;
    }
}
