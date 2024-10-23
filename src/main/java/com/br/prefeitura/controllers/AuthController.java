package com.br.prefeitura.controllers;

import com.br.prefeitura.dto.auth.LoginRequestDTO;
import com.br.prefeitura.dto.auth.RegisterRequestDTO;
import com.br.prefeitura.dto.auth.ResponseDTO;
import com.br.prefeitura.entities.Prefeitura;
import com.br.prefeitura.entities.Secretaria;
import com.br.prefeitura.entities.Usuario;
import com.br.prefeitura.enums.Privilegio;
import com.br.prefeitura.infra.security.TokenService;
import com.br.prefeitura.repositories.PrefeituraRepository;
import com.br.prefeitura.repositories.SecretariaRepository;
import com.br.prefeitura.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UsuarioRepository repository;
    private final PrefeituraRepository prefeituraRepository;
    private final SecretariaRepository secretariaRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO body){
        Usuario user = this.repository.findByEmail(body.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));
        if(passwordEncoder.matches(body.getSenha(), user.getSenha())) {
            String token = this.tokenService.generateToken(user);
            return ResponseEntity.ok(new ResponseDTO(user.getNome(), token));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequestDTO body) {
        Optional<Usuario> user = this.repository.findByEmail(body.getEmail());

        if (user.isEmpty()) {
            Usuario newUser = new Usuario();

            // Codificar e definir senha
            newUser.setSenha(passwordEncoder.encode(body.getSenha()));
            newUser.setEmail(body.getEmail());
            newUser.setNome(body.getNome());

            // Definir privilégio padrão (exemplo: USER)
            if (body.getPrivilegio().equals(Privilegio.ADMIN)) {
                newUser.setPrivilegio(Privilegio.ADMIN);
            } else if (body.getPrivilegio().equals(Privilegio.CNPJ)) {
                newUser.setPrivilegio(Privilegio.CNPJ);
            }

            this.repository.save(newUser);

            String token = this.tokenService.generateToken(newUser);
            return ResponseEntity.ok(new ResponseDTO(newUser.getNome(), token));
        }
        return ResponseEntity.badRequest().build();
    }
}