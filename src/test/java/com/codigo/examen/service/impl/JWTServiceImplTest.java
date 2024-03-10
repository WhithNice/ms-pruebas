package com.codigo.examen.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JWTServiceImplTest {
    @InjectMocks
    private JWTServiceImpl jwtService;

    @Test
    void generateTokenOk() {
        UserDetails userDetails = User.builder().username("test").password("password").roles("USER").build();

        String token = jwtService.generateToken(userDetails);
    }

    @Test
    void validateToken() {
        UserDetails userDetails = User.builder().username("test").password("password").roles("USER").build();
        String token = jwtService.generateToken(userDetails);

        boolean isValid = jwtService.validateToken(token, userDetails);

        assertTrue(isValid);
    }



}