package com.codigo.examen.service.impl;

import com.codigo.examen.entity.Usuario;
import com.codigo.examen.repository.UsuarioRepository;
import com.codigo.examen.request.SignInRequest;
import com.codigo.examen.response.AuthenticationResponse;
import com.codigo.examen.service.JWTService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceImplTest {
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JWTService jwtService;
    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @Test
    void signinOk() {
        SignInRequest signInRequest = new SignInRequest();
        signInRequest.setUsername("test");
        signInRequest.setPassword("password");

        Mockito.when(usuarioRepository.findByUsername("test")).thenReturn(Optional.of(new Usuario()));
        Mockito.when(jwtService.generateToken(any())).thenReturn("holatoken");

        AuthenticationResponse authenticationResponse = authenticationService.signin(signInRequest);

        assertNotNull(authenticationResponse);
        assertEquals("holatoken", authenticationResponse.getToken());
    }
}