package com.br.prefeitura.services;

import com.br.prefeitura.entities.Usuario;
import com.br.prefeitura.exceptions.UsuarioNaoEncontradoException;
import com.br.prefeitura.repositories.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    private UsuarioService usuarioService;
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    void setUp() {
        usuarioRepository = Mockito.mock(UsuarioRepository.class);
        usuarioService = new UsuarioService();
        usuarioService.usuarioRepository = usuarioRepository;
    }

    @Test
    void testSave() {
        Usuario usuario = new Usuario(); // Preencha com dados reais
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario savedUsuario = usuarioService.save(usuario);

        assertNotNull(savedUsuario);
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    void testFindById_ExistingUser() {
        Long userId = 1L;
        Usuario usuario = new Usuario(); // Preencha com dados reais
        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));

        Optional<Usuario> foundUsuario = usuarioService.findById(userId);

        assertTrue(foundUsuario.isPresent());
        assertEquals(usuario, foundUsuario.get());
    }

    @Test
    void testFindById_NonExistingUser() {
        Long userId = 1L;
        when(usuarioRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UsuarioNaoEncontradoException.class, () -> usuarioService.findById(userId));
    }
}
