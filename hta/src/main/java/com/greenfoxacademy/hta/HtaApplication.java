package com.greenfoxacademy.hta;

import com.greenfoxacademy.hta.models.log.LogType;
import com.greenfoxacademy.hta.models.user.*;
import com.greenfoxacademy.hta.repositories.log.ILogTypeRepository;
import com.greenfoxacademy.hta.repositories.IRoleRepository;
import com.greenfoxacademy.hta.repositories.IUserRepository;
import com.greenfoxacademy.hta.repositories.healthylivingrepositories.IWeightRepository;
import com.greenfoxacademy.hta.services.city.ICityService;
import com.greenfoxacademy.hta.services.user.IUserService;
import com.greenfoxacademy.hta.models.medication.Medication;
import com.greenfoxacademy.hta.models.medication.MedicationIntake;
import com.greenfoxacademy.hta.models.medication.Units;
import com.greenfoxacademy.hta.models.notifications.Notification;
import com.greenfoxacademy.hta.models.roles.Role;
import com.greenfoxacademy.hta.models.roles.RoleName;
import com.greenfoxacademy.hta.repositories.*;
import com.greenfoxacademy.hta.repositories.medications.IMedicationIntakeRepository;
import com.greenfoxacademy.hta.repositories.medications.IMedicationRepository;
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
    CommandLineRunner run(IUserService iUserService, IRoleRepository iRoleRepository, IUserRepository iUserRepository,
                          IBloodPressureRepository iBloodPressureRepository, IHeartRateRepository iHeartRateRepository,
                          IWeightRepository iWeightRepository, IMedicationIntakeRepository iMedicationIntakeRepository,
                          IMedicationRepository iMedicationRepository, INotificationRepository iNotificationRepository,
                          PasswordEncoder passwordEncoder, ILogTypeRepository iLogTypeRepository, ICityService iCityService) {
        return args -> {
            iUserService.saveRole(new Role(RoleName.USER));
            iUserService.saveRole(new Role(RoleName.ADMIN));
            Role role = iRoleRepository.findByRoleName(RoleName.ADMIN);
            Role role2 = iRoleRepository.findByRoleName(RoleName.USER);
            addAdmin(iUserService, iUserRepository, passwordEncoder, role2, role);
            addDummyUser(iUserService, iUserRepository, passwordEncoder, role2);
            addLogType(iLogTypeRepository);
            addCities(iCityService);
            addDataToUser1ForNotifications(iUserRepository, iBloodPressureRepository, iHeartRateRepository, iWeightRepository,
                    iMedicationIntakeRepository, iMedicationRepository, iNotificationRepository);
            addUsers(passwordEncoder, iUserService, iCityService, iRoleRepository);
        };
    }

    private void addDataToUser1ForNotifications(IUserRepository iUserRepository, IBloodPressureRepository iBloodPressureRepository,
                                                IHeartRateRepository iHeartRateRepository, IWeightRepository iWeightRepository,
                                                IMedicationIntakeRepository iMedicationIntakeRepository,
                                                IMedicationRepository iMedicationRepository, INotificationRepository iNotificationRepository) {
        User user = iUserRepository.findByEmail("user1@gmail.com").get();

        BloodPressure bloodPressure = new BloodPressure(
                130f,
                75f,
                LocalDateTime.of(2020, 5, 27, 2, 0, 0, 0)
        );
        bloodPressure.setUser(user);
        iBloodPressureRepository.save(bloodPressure);

        HeartRate heartRate = new HeartRate(
                72f,
                LocalDateTime.of(2021, 6, 1, 0, 0, 0, 0)
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


    public void addAdmin(IUserService iUserService, IUserRepository iUserRepository,
                         PasswordEncoder passwordEncoder, Role role2, Role role) {
        iUserService.saveUser(new User("Admin", "admin@gmail.com", passwordEncoder.encode("adminPassword"), new ArrayList<>()));
        User user = iUserRepository.findByEmail("admin@gmail.com").orElse(null);
        assert user != null;
        user.getRoles().add(role);
        user.getRoles().add(role2);
        iUserService.saveUser(user);
    }

    public void addDummyUser(IUserService iUserService, IUserRepository iUserRepository,
                             PasswordEncoder passwordEncoder, Role role2) {
        List<User> users = Arrays.asList(
                new User("User1", "user1@gmail.com", passwordEncoder.encode("password1"),
                        "DummyUser1", BiologicalGender.MALE, LocalDate.of(1988, 1, 4), 198.5),
                new User("User2", "user2@gmail.com", passwordEncoder.encode("password2"),
                        "DummyUser2", BiologicalGender.FEMALE, LocalDate.of(1986, 5, 6), 168.5),
                new User("User3", "user3@gmail.com", passwordEncoder.encode("password3"),
                        "DummyUser3", BiologicalGender.MALE, LocalDate.of(1976, 12, 21), 168.5));
        for (User userItem : users) {
            iUserService.saveUser(userItem);
            User user2 = iUserRepository.findByEmail(userItem.getEmail()).orElse(null);
            assert user2 != null;
            user2.getRoles().add(role2);
            iUserService.saveUser(user2);
        }
    }

    public void addLogType(ILogTypeRepository iLogTypeRepository) {
        iLogTypeRepository.save(new LogType("registration", "A new account registered by "));
        iLogTypeRepository.save(new LogType("login", "Logged in : "));
        iLogTypeRepository.save(new LogType("pwchange", "Password changed by "));
        iLogTypeRepository.save(new LogType("adminpwchange", "The admin reseted the password of "));
        iLogTypeRepository.save(new LogType("adminuserdelete", "The admin deleted the account of "));
    }

    public void addUsers(PasswordEncoder passwordEncoder, IUserService iUserService, ICityService iCityService, IRoleRepository iRoleRepository){
        User user1 = new User();
        user1.setUsername("akoska");
        user1.setEmail("akos@gmail.com");
        user1.setPassword(passwordEncoder.encode("xx"));
        user1.setRealName("Akos");
        user1.setBiologicalGender(BiologicalGender.MALE);
        user1.setBirthDate(LocalDate.of(1999,10,10));
        user1.setCity(iCityService.findByCityName("Szombathely"));
        user1.setActive(true);
        user1.getRoles().add(iRoleRepository.findByRoleName(RoleName.USER));
        iUserService.saveUser(user1);

        User user2 = new User();
        user2.setUsername("bela");
        user2.setEmail("bela@gmail.com");
        user2.setPassword(passwordEncoder.encode("xx"));
        user2.setRealName("Bela");
        user2.setBiologicalGender(BiologicalGender.MALE);
        user2.setBirthDate(LocalDate.of(2000,1,25));
        user2.setCity(iCityService.findByCityName("Budapest"));
        user2.setActive(true);
        user2.getRoles().add(iRoleRepository.findByRoleName(RoleName.USER));
        iUserService.saveUser(user2);

        User user3 = new User();
        user3.setUsername("gizi");
        user3.setEmail("gizi@gmail.com");
        user3.setPassword(passwordEncoder.encode("xx"));
        user3.setRealName("Gizi");
        user3.setBiologicalGender(BiologicalGender.FEMALE);
        user3.setBirthDate(LocalDate.of(1980,11,5));
        user3.setCity(iCityService.findByCityName("Győr"));
        user3.setActive(true);
        user3.getRoles().add(iRoleRepository.findByRoleName(RoleName.USER));
        iUserService.saveUser(user3);

        User user4 = new User();
        user4.setUsername("balazs");
        user4.setEmail("balazs@gmail.com");
        user4.setPassword(passwordEncoder.encode("xx"));
        user4.setRealName("Balazs");
        user4.setBiologicalGender(BiologicalGender.MALE);
        user4.setBirthDate(LocalDate.of(1985,8,4));
        user4.setCity(iCityService.findByCityName("Salgótarján"));
        user4.setActive(true);
        user4.getRoles().add(iRoleRepository.findByRoleName(RoleName.USER));
        iUserService.saveUser(user4);

        User user5 = new User();
        user5.setUsername("tamás");
        user5.setEmail("ordog@gmail.com");
        user5.setPassword(passwordEncoder.encode("xx"));
        user5.setRealName("Ordog");
        user5.setBiologicalGender(BiologicalGender.MALE);
        user5.setBirthDate(LocalDate.of(1989,9,8));
        user5.setCity(iCityService.findByCityName("Salgótarján"));
        user5.setActive(true);
        user5.getRoles().add(iRoleRepository.findByRoleName(RoleName.USER));
        iUserService.saveUser(user5);

        User user6 = new User();
        user6.setUsername("Huszar");
        user6.setEmail("HuszarAdam@gmail.com");
        user6.setPassword(passwordEncoder.encode("xx"));
        user6.setRealName("Adam");
        user6.setBiologicalGender(BiologicalGender.MALE);
        user6.setBirthDate(LocalDate.of(1979,9,8));
        user6.setCity(iCityService.findByCityName("Miskolc"));
        user6.setActive(true);
        user6.getRoles().add(iRoleRepository.findByRoleName(RoleName.USER));
        iUserService.saveUser(user6);
    }

    private void addCities(ICityService iCityService){
        City city1 = new City();
        city1.setCityName("Szombathely");
        city1.setYLatitude(47.23155);
        city1.setXLongitude(16.62740);
        iCityService.saveCity(city1);

        City city2 = new City();
        city2.setCityName("Debrecen");
        city2.setYLatitude(47.53158);
        city2.setXLongitude(21.62533);
        iCityService.saveCity(city2);

        City city3 = new City();
        city3.setCityName("Nyíregyháza");
        city3.setYLatitude(47.95513);
        city3.setXLongitude(21.71743);
        iCityService.saveCity(city3);

        City city4 = new City();
        city4.setCityName("Szolnok");
        city4.setYLatitude(47.17350);
        city4.setXLongitude(20.20120);
        iCityService.saveCity(city4);

        City city5 = new City();
        city5.setCityName("Békéscsaba");
        city5.setYLatitude(46.68420);
        city5.setXLongitude(21.08670);
        iCityService.saveCity(city5);

        City city6 = new City();
        city6.setCityName("Miskolc");
        city6.setYLatitude(48.10435);
        city6.setXLongitude(20.79127);
        iCityService.saveCity(city6);

        City city7 = new City();
        city7.setCityName("Kecskemét");
        city7.setYLatitude(46.90758);
        city7.setXLongitude(19.69220);
        iCityService.saveCity(city7);

        City city8 = new City();
        city8.setCityName("Szeged");
        city8.setYLatitude(46.25180);
        city8.setXLongitude(20.15080);
        iCityService.saveCity(city8);

        City city9 = new City();
        city9.setCityName("Salgótarján");
        city9.setYLatitude(48.10377);
        city9.setXLongitude(18.80680);
        iCityService.saveCity(city9);

        City city10 = new City();
        city10.setCityName("Tatabánya");
        city10.setYLatitude(47.58505);
        city10.setXLongitude(18.39838);
        iCityService.saveCity(city10);

        City city11 = new City();
        city11.setCityName("Szekszárd");
        city11.setYLatitude(46.34858);
        city11.setXLongitude(18.70190);
        iCityService.saveCity(city11);

        City city12 = new City();
        city12.setCityName("Székesfehérvár");
        city12.setYLatitude(47.19138);
        city12.setXLongitude(18.40980);
        iCityService.saveCity(city12);

        City city13 = new City();
        city13.setCityName("Veszprém");
        city13.setYLatitude(47.09253);
        city13.setXLongitude(17.90783);
        iCityService.saveCity(city13);

        City city14 = new City();
        city14.setCityName("Kaposvár");
        city14.setYLatitude(46.35648);
        city14.setXLongitude(17.78828);
        iCityService.saveCity(city14);

        City city15 = new City();
        city15.setCityName("Pécs");
        city15.setYLatitude(46.07132);
        city15.setXLongitude(18.23317);
        iCityService.saveCity(city15);

        City city16 = new City();
        city16.setCityName("Eger");
        city16.setYLatitude(47.90265);
        city16.setXLongitude(20.37643);
        iCityService.saveCity(city16);

        City city17 = new City();
        city17.setCityName("Zalaegerszeg");
        city17.setYLatitude(46.84533);
        city17.setXLongitude(16.84722);
        iCityService.saveCity(city17);

        City city18 = new City();
        city18.setCityName("Győr");
        city18.setYLatitude(47.68405);
        city18.setXLongitude(17.63550);
        iCityService.saveCity(city18);

        City city19 = new City();
        city19.setCityName("Budapest");
        city19.setYLatitude(47.49955);
        city19.setXLongitude(19.04720);
        iCityService.saveCity(city19);
    }
}