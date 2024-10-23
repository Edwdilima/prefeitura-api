package com.br.prefeitura.exceptions;

public class UsuarioNaoEncontradoException extends RuntimeException {
    public UsuarioNaoEncontradoException(Long id) {
        super("Usuario com ID " + id + " não encontrado.");
    }
}