package com.codigo.examen.controller;

import com.codigo.examen.entity.Usuario;
import com.codigo.examen.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class UsuarioControllerTest {
    @Mock
    private UsuarioService usuarioService;
    @InjectMocks
    private UsuarioController usuarioController;

    @Test
    void crearUsuario() {
        Usuario usuario = new Usuario();
        usuario.setUsername("test");

        when(usuarioService.createUsuario(usuario)).thenReturn(ResponseEntity.ok(usuario));

        ResponseEntity<Usuario> response = usuarioController.createUsuario(usuario);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(usuario, response.getBody());
    }

    @Test
    void getUsuarioById() {
        Long id = 1L;
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(id);

        when(usuarioService.getUsuarioById(id)).thenReturn(ResponseEntity.ok(usuario));

        ResponseEntity<Usuario> response = usuarioController.getUsuarioById(id);
    }

    @Test
    void updateUsuario() {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1L);
        when(usuarioService.updateUsuario(1L, usuario)).thenReturn(ResponseEntity.ok(usuario));

        ResponseEntity<Usuario> response = usuarioController.updateUsuario(1L, usuario);
    }

    @Test
    void deleteUsuario() {

//        when(usuarioService.deleteUsuario(1L)).thenReturn( );

//        ResponseEntity<Usuario> response = usuarioController.deleteUsuario(1L);
    }
}