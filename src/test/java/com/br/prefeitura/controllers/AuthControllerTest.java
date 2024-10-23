package com.br.prefeitura.controllers;

import com.br.prefeitura.dto.auth.LoginRequestDTO;
import com.br.prefeitura.dto.auth.RegisterRequestDTO;
import com.br.prefeitura.dto.auth.ResponseDTO;
import com.br.prefeitura.entities.Usuario;
import com.br.prefeitura.infra.security.TokenService;
import com.br.prefeitura.repositories.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private TokenService tokenService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLogin_Success() {

        String email = "weslleyTestes@gmail.com";
        String password = "senha123";
        LoginRequestDTO loginRequest = new LoginRequestDTO(email, password);
        Usuario usuario = new Usuario(); // criar o objeto usuario e definir propriedades

        // Mock do repositório e tokenService
        when(usuarioRepository.findByEmail(email)).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches(password, usuario.getSenha())).thenReturn(true);
        when(tokenService.generateToken(usuario)).thenReturn("mockedToken");

        ResponseEntity<ResponseDTO> response = authController.login(loginRequest);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testLogin_UserNotFound() {

        String email = "naoExiste@email.com";
        String password = "senhaNova";
        LoginRequestDTO loginRequest = new LoginRequestDTO(email, password);

        // Mock do repositório
        when(usuarioRepository.findByEmail(email)).thenReturn(Optional.empty());

        ResponseEntity<ResponseDTO> response = authController.login(loginRequest);
        assertEquals(400, response.getStatusCodeValue());
    }

    // Adicione outros métodos de teste conforme necessário
}
