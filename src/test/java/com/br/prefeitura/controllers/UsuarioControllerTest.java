package com.br.prefeitura.controllers;

import com.br.prefeitura.controllers.UsuarioController;
import com.br.prefeitura.entities.Usuario;
import com.br.prefeitura.services.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class UsuarioControllerTest {

    @InjectMocks
    private UsuarioController usuarioController;

    @Mock
    private UsuarioService usuarioService;

    private Usuario usuario;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("weslley@test.com");
        // Configure outros atributos conforme necessário
    }

    @Test
    public void testGetById_Success() {
        when(usuarioService.findById(1L)).thenReturn(java.util.Optional.of(usuario));

        ResponseEntity<Usuario> response = usuarioController.getById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(usuario, response.getBody());
    }

    @Test
    public void testGetById_NotFound() {
        when(usuarioService.findById(1L)).thenReturn(java.util.Optional.empty());

        ResponseEntity<Usuario> response = usuarioController.getById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
