package com.example.czateria_springboot_2_7.security;

import com.auth0.jwt.JWT;
import com.example.czateria_springboot_2_7.app_user.AppUser;
import com.example.czateria_springboot_2_7.app_user.AuthorizationResponse;
import com.example.czateria_springboot_2_7.app_user.UserDetailsAdapter;
import com.example.czateria_springboot_2_7.error.ErrorEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.czateria_springboot_2_7.security.EncryptionConfiguration.getAlgorithm;
import static com.example.czateria_springboot_2_7.utlis.DateUtils.setHours;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        AppUser credentials;
        String username = null;
        String password = null;
        try {
            credentials = new ObjectMapper().readValue(request.getInputStream(), AppUser.class);
            username = credentials.getUsername();
            password = credentials.getUserPassword();
        } catch (IOException e) {
            e.printStackTrace();
        }

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(FORBIDDEN.value());
        response.setContentType(APPLICATION_JSON_VALUE);
        ErrorEntity<String> error = new ErrorEntity<>();
        error.setCode(FORBIDDEN.value());
        error.setMessage(FORBIDDEN.getReasonPhrase());
        if (failed.getMessage().equals("Bad credentials")) {
            error.setDetails("Wrong password");
        } else {
            error.setDetails(failed.getMessage());
        }
        new ObjectMapper().writeValue(response.getOutputStream(), error);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        UserDetailsAdapter user = (UserDetailsAdapter) authentication.getPrincipal();
        List<String> roles = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        String accessToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(setHours(12))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", roles)
                .sign(getAlgorithm());

//        String refreshToken = JWT.create()
//                .withSubject(user.getUsername())
//                .withExpiresAt(setHours(23))
//                .withIssuer(request.getRequestURL().toString())
//                .sign(getAlgorithm());

        AuthorizationResponse authorizationResponse = new AuthorizationResponse(
                user.getUsername(),
                roles,
                accessToken,
                LocalDateTime.now().plusHours(12).toString());
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), authorizationResponse);
    }
}
