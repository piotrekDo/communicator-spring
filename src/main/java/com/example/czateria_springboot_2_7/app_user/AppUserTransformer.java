package com.example.czateria_springboot_2_7.app_user;

import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class AppUserTransformer {

    AppUserDto appUserToDto(AppUser appUser) {
        return new AppUserDto(
                appUser.getId(),
                appUser.getUsername(),
                appUser.getUserEmail(),
                appUser.getUserRoles().stream()
                        .map(UserRole::getRoleName)
                        .collect(Collectors.toList())
        );
    }

}
