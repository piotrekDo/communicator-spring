package com.example.czateria_springboot_2_7.app_user;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class AuthorizationResponse {
    private String username;
    private List<String> userRoles;
    private String jwtToken;
    private String jwtExpiresAt;
}
