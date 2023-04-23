package com.greenfoxacademy.hta;

import com.greenfoxacademy.hta.models.*;
import com.greenfoxacademy.hta.models.medication.Medication;
import com.greenfoxacademy.hta.models.medication.MedicationIntake;
import com.greenfoxacademy.hta.models.medication.Units;
import com.greenfoxacademy.hta.models.notifications.Notification;
import com.greenfoxacademy.hta.models.roles.Role;
import com.greenfoxacademy.hta.models.roles.RoleName;
import com.greenfoxacademy.hta.repositories.*;
import com.greenfoxacademy.hta.repositories.medications.IMedicationIntakeRepository;
import com.greenfoxacademy.hta.repositories.medications.IMedicationRepository;
import com.greenfoxacademy.hta.services.user.IUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@SpringBootApplication
public class HtaApplication {

    public static void main(String[] args) {
        SpringApplication.run(HtaApplication.class, args);
    }

    @Bean
    CommandLineRunner run (IUserService iUserService, IRoleRepository iRoleRepository, IUserRepository iUserRepository,
                           IBloodPressureRepository iBloodPressureRepository, IHeartRateRepository iHeartRateRepository,
                           IWeightRepository iWeightRepository, IMedicationIntakeRepository iMedicationIntakeRepository,
                           IMedicationRepository iMedicationRepository, INotificationRepository iNotificationRepository,
                           PasswordEncoder passwordEncoder) {
      return  args -> {
            iUserService.saveRole(new Role(RoleName.USER));
            iUserService.saveRole(new Role(RoleName.ADMIN));
            Role role = iRoleRepository.findByRoleName(RoleName.ADMIN);
            Role role2 = iRoleRepository.findByRoleName(RoleName.USER);
            addAdmin(iUserService, iUserRepository, passwordEncoder,role2,role);
            addDummyUser(iUserService, iUserRepository, passwordEncoder,role2);
            addDataToUser1ForNotifications(iUserRepository, iBloodPressureRepository, iHeartRateRepository, iWeightRepository,
                    iMedicationIntakeRepository, iMedicationRepository, iNotificationRepository);
       };
    }

    private void addDataToUser1ForNotifications(IUserRepository iUserRepository, IBloodPressureRepository iBloodPressureRepository,
                                                IHeartRateRepository iHeartRateRepository, IWeightRepository iWeightRepository,
                                                IMedicationIntakeRepository iMedicationIntakeRepository,
                                                IMedicationRepository iMedicationRepository, INotificationRepository iNotificationRepository) {
        User user = iUserRepository.findByEmail("user1@gmail.com").get();

        BloodPressure bloodPressure = new BloodPressure(
                LocalDateTime.of(2020, 5, 27, 2, 0, 0, 0),
                130f,
                75f
        );
        bloodPressure.setUser(user);
        iBloodPressureRepository.save(bloodPressure);

        HeartRate heartRate = new HeartRate(
                LocalDateTime.of(2021, 6, 12, 2, 0, 0, 0),
                72f
        );
        iHeartRateRepository.save(heartRate);
        heartRate.setUser(user);
        iHeartRateRepository.save(heartRate);

        Weight weight = new Weight(
                LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 2, 0, 0)),
                80f
        );
        weight.setUser(user);
        iWeightRepository.save(weight);

        Medication medication1 = new Medication(
                true,
                "Rapamune",
                0.2f,
                Units.ML,
                2,
                LocalDateTime.of(2023, 11, 13, 2, 0, 0, 0)
        );
        Medication medication2 = new Medication(
                true,
                "Fenistil",
                13f,
                Units.DROP,
                2,
                LocalDateTime.of(2023, 5, 2, 2, 0, 0, 0)
        );
        medication1.setUser(user);
        medication2.setUser(user);
        iMedicationRepository.save(medication1);
        iMedicationRepository.save(medication2);

        MedicationIntake medicationIntake1 = new MedicationIntake(
                LocalDateTime.of(LocalDate.now(), LocalTime.of(11, 12, 0, 0)));
        MedicationIntake medicationIntake2 = new MedicationIntake(
                LocalDateTime.of(LocalDate.now(), LocalTime.of(20, 18, 0, 0)));
        MedicationIntake medicationIntake3 = new MedicationIntake(
                LocalDateTime.of(LocalDate.now(), LocalTime.of(13, 10, 0, 0)));
        medicationIntake1.setMedication(medication1);
        medicationIntake2.setMedication(medication1);
        medicationIntake3.setMedication(medication2);
        iMedicationIntakeRepository.save(medicationIntake1);
        iMedicationIntakeRepository.save(medicationIntake2);
        iMedicationIntakeRepository.save(medicationIntake3);

        Notification notification = new Notification(true, 3,
                true, 5, true, 1,
                true);
        iNotificationRepository.save(notification);
        user.setNotification(notification);
        iUserRepository.save(user);
    }


    public void addAdmin(IUserService iUserService, IUserRepository iUserRepository ,
                         PasswordEncoder passwordEncoder, Role role2, Role role ) {
      iUserService.saveUser(new User("Admin", "admin@gmail.com", passwordEncoder.encode("adminPassword"), new ArrayList<>()));
      User user = iUserRepository.findByEmail("admin@gmail.com").orElse(null);
      user.getRoles().add(role);
      user.getRoles().add(role2);
      iUserService.saveUser(user);
    }

    public void addDummyUser(IUserService iUserService, IUserRepository iUserRepository ,
                             PasswordEncoder passwordEncoder, Role role2) {
      List<User> users = Arrays.asList(
              new User("User1", "user1@gmail.com", passwordEncoder.encode("password1"),
                      "DummyUser1", BiologicalGender.MALE, LocalDate.of(1988,1,4), 198.5),
              new User("User2", "user2@gmail.com", passwordEncoder.encode("password2"),
                      "DummyUser2", BiologicalGender.FEMALE, LocalDate.of(1986,5,6), 168.5),
              new User("User3", "user3@gmail.com", passwordEncoder.encode("password3"),
                      "DummyUser3", BiologicalGender.MALE, LocalDate.of(1976,12,21), 168.5));
      for (User userItem: users) {
        iUserService.saveUser(userItem);
        User user2 = iUserRepository.findByEmail(userItem.getEmail()).orElse(null);
        user2.getRoles().add(role2);
        iUserService.saveUser(user2);
      }
    }
}