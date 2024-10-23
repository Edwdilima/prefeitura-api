package com.br.prefeitura.services;

import com.br.prefeitura.exceptions.UsuarioNaoEncontradoException;
import com.br.prefeitura.entities.Usuario;
import com.br.prefeitura.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;


    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }


    public Optional<Usuario> findById(Long id) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if(usuarioOptional.isPresent()){
            return usuarioOptional;
        }else{
            throw new UsuarioNaoEncontradoException(id);
        }
    }

}
