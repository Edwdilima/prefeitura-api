package com.br.prefeitura.dtos;


import com.br.prefeitura.enums.StatusLicitacao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class LicitacaoDTO {
    @NotNull(message = "O campo número não pode ser nulo")
    @NotBlank(message = "O campo número não pode ser vazio ou em branco")
    @Size(max = 20, message = "O campo número não pode ter mais de 20 caracteres")
    private String numero;

    @NotNull(message = "O campo valor estimado não pode ser nulo")
    private BigDecimal valorEstimado;

    @NotNull(message = "O campo tipo não pode ser nulo")
    @NotBlank(message = "O campo tipo não pode ser vazio ou em branco")
    @Size(max = 50, message = "O campo tipo não pode ter mais de 50 caracteres")
    private String tipo;

    @NotNull(message = "O campo status da licitação não pode ser nulo")
    private StatusLicitacao statusLicitacao;

    public LicitacaoDTO(){}
    public LicitacaoDTO(String numero, BigDecimal valorEstimado, String tipo, StatusLicitacao statusLicitacao) {
        this.numero = numero;
        this.valorEstimado = valorEstimado;
        this.tipo = tipo;
        this.statusLicitacao = statusLicitacao;
    }

    // Getters and Setters
    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public BigDecimal getValorEstimado() {
        return valorEstimado;
    }

    public void setValorEstimado(BigDecimal valorEstimado) {
        this.valorEstimado = valorEstimado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public StatusLicitacao getStatusLicitacao() {
        return statusLicitacao;
    }

    public void setStatusLicitacao(StatusLicitacao statusLicitacao) {
        this.statusLicitacao = statusLicitacao;
    }

    public void setId(long l) {
    }
}
