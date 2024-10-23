package com.br.prefeitura.dtos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ObraDTO {

    @NotNull(message = "O campo descrição não pode ser nulo")
    @NotBlank(message = "O campo descrição não pode ser vazio ou em branco")
    @Size(max = 250, message = "O campo descrição não pode ter mais de 250 caracteres")
    private String descricao;


    // Construtores
    public ObraDTO(){}
    public ObraDTO(String descricao) {
        this.descricao = descricao;
    }


    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
