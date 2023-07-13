package com.example.czateria_springboot_2_7;

import com.example.czateria_springboot_2_7.app_user.AppUser;
import com.example.czateria_springboot_2_7.app_user.AppUserRepository;
import com.example.czateria_springboot_2_7.app_user.UserRole;
import com.example.czateria_springboot_2_7.app_user.UserRoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@AllArgsConstructor
public class Initializer {

    private final UserRoleRepository userRoleRepository;
    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    @PostConstruct
    public void run(){
        UserRole userRole = new UserRole("USER");
        UserRole moderatorRole = new UserRole("MODERATOR");
        UserRole adminRole = new UserRole("ADMIN");
        userRoleRepository.save(userRole);
        userRoleRepository.save(moderatorRole);
        userRoleRepository.save(adminRole);

        AppUser userPiotrek = new AppUser("piotrek", "piotrek@fake.com", "");
        userPiotrek.setUserPassword(passwordEncoder.encode(userPiotrek.getUserPassword()));
        userPiotrek.getUserRoles().add(userRole);
        userPiotrek.getUserRoles().add(moderatorRole);
        userPiotrek.getUserRoles().add(adminRole);
        appUserRepository.save(userPiotrek);

        AppUser user1 = new AppUser("user1", "user1@fake.com", "");
        user1.setUserPassword(passwordEncoder.encode(user1.getUserPassword()));
        user1.getUserRoles().add(userRole);
        appUserRepository.save(user1);

        AppUser user2 = new AppUser("user2", "user1@fake.com", "");
        user2.setUserPassword(passwordEncoder.encode(user2.getUserPassword()));
        user2.getUserRoles().add(userRole);
        appUserRepository.save(user2);
    }
}
