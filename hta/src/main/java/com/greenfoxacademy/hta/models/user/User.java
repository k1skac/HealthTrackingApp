package com.greenfoxacademy.hta.models.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.greenfoxacademy.hta.models.goalentities.Goal;
import com.greenfoxacademy.hta.models.healthylivingentities.DailyCalorieIntake;
import com.greenfoxacademy.hta.models.healthylivingentities.ExerciseTime;
import com.greenfoxacademy.hta.models.bloodlabdata.BloodLabData;
import com.greenfoxacademy.hta.models.log.Log;
import com.greenfoxacademy.hta.models.medication.Medication;
import com.greenfoxacademy.hta.models.notifications.Notification;
import com.greenfoxacademy.hta.models.nutrition.Meal;
import com.greenfoxacademy.hta.models.roles.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable , UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String username ;
    private String email;
    private String password ;
    @CreationTimestamp
    private LocalDateTime createdAt;
    private boolean active;
    private String realName;
    private BiologicalGender biologicalGender;
    private LocalDate birthDate;
    private double height;
    @OneToOne()
    private Notification notification;
    @ManyToMany(fetch = FetchType.EAGER)
    private List <Role> roles = new ArrayList<>();
    @ManyToOne()
    private City city;
    @OneToMany(mappedBy = "user")
    private List<Log> logs = new ArrayList<>();
    @OneToMany(mappedBy = "user")
    private List<BloodPressure> bloodPressures = new ArrayList<>();
    @OneToMany(mappedBy = "user")
    private List<HeartRate> heartRates = new ArrayList<>();
    private List<BNOCode> illnessCodes = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Weight> weights = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<DailyCalorieIntake> dailyCalories = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<ExerciseTime> dailyExercise = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Goal> goals = new ArrayList<>();
    @OneToMany(mappedBy = "user")
    private List<Medication> medications= new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Meal> meals;
    @OneToOne()
    private BloodLabData bloodLabData;
    private int userScore;

    public User (String username, String email , String password , List<Role> roles) {
      this.username=username;
      this.email= email ;
      this.password=password ;
      this.roles=roles ;
      this.active=true;
    }

    public User( String username, String email, String password,  String realName, BiologicalGender biologicalGender,
                 LocalDate birthDate, double height) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.realName = realName;
        this.biologicalGender = biologicalGender;
        this.birthDate = birthDate;
        this.height=height;
        this.active=true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        this.roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getRoleName())));
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}