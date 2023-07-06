package com.example.czateria_springboot_2_7.app_user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@AllArgsConstructor
@Data
@EqualsAndHashCode
public class AppUserDto {

    private Long id;
    private String username;
    private String userEmail;
    private List<String> userRoles;
}
