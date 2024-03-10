package com.codigo.examen.service.impl;

import com.codigo.examen.entity.Rol;
import com.codigo.examen.entity.Usuario;
import com.codigo.examen.repository.RolRepository;
import com.codigo.examen.repository.UsuarioRepository;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
class UsuarioServiceImplTest {
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private RolRepository rolRepository;
    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    @Test
    void createUsuarioUsuarioNoExiste() {
        Rol rol = new Rol();
        rol.setIdRol(1L);
        rol.setNombreRol("USER");

        Usuario usuario = new Usuario();
        usuario.setUsername("test");
        usuario.setPassword("password");
        usuario.setRoles(Collections.singleton(rol));

        when(usuarioRepository.findByUsername("test")).thenReturn(Optional.empty());
        when(rolRepository.findById(rol.getIdRol())).thenReturn(Optional.of(rol));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        ResponseEntity<Usuario> response = usuarioService.createUsuario(usuario);

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void detalleUsuario() {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1L);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        ResponseEntity<Usuario> response = usuarioService.getUsuarioById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(usuario, response.getBody());
    }

    @Test
    void updateUsuarioOk() {
        Rol rol = new Rol();
        rol.setIdRol(1L);
        rol.setNombreRol("USER");

        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1L);
        usuario.setUsername("nice");
        usuario.setRoles(Collections.singleton(rol));

        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setUsername("hola");

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        ResponseEntity<Usuario> response = usuarioService.updateUsuario(1L, usuario);
    }

    @Test
    void updateUsuarioNotFound() {

        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Usuario> response = usuarioService.updateUsuario(1L, new Usuario());

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void updateUsuarioExistUsername() {
        Usuario usuario = new Usuario();
        usuario.setUsername("hola");

        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setIdUsuario(1L);
        usuarioExistente.setUsername("test");
        usuarioExistente.setRoles(Collections.singleton(new Rol()));

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioExistente));
        when(usuarioRepository.findByUsername(anyString())).thenReturn(Optional.of(usuario));

        ResponseEntity<Usuario> response = usuarioService.updateUsuario(1L, usuario);

        assertEquals(400, response.getStatusCodeValue());
        assertNull(response.getBody());
    }


    @Test
    void eliminarUsuarioOk() {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1L);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        ResponseEntity<Usuario> response = usuarioService.deleteUsuario(1L);
    }

    @Test
    void deleteUsuario_UsuarioNoExistente_DeberiaRetornarNotFound() {

        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Usuario> response = usuarioService.deleteUsuario(1L);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void userDetailsServiceOk() {
        String username = "test";
        Usuario usuario = new Usuario();
        usuario.setUsername(username);

        when(usuarioRepository.findByUsername(username)).thenReturn(Optional.of(usuario));

        UserDetails userDetails = usuarioService.userDetailsService().loadUserByUsername(username);

        assertNotNull(userDetails);
    }
}