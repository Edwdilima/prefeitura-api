package com.br.prefeitura.entities;

import com.br.prefeitura.enums.StatusProposta;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Proposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O campo não pode ser nulo")
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @NotNull(message = "O campo não pode ser nulo")
    @ManyToOne
    @JoinColumn(name = "prefeitura_id", nullable = false)
    private Prefeitura prefeitura;

    @NotNull(message = "O campo não pode ser nulo")
    @NotEmpty(message = "O campo não pode ser vazio")
    @Column(name = "valor", precision = 10, scale = 2, nullable = false)
    private BigDecimal valor;

    @NotNull(message = "O campo não pode ser nulo")
    @NotBlank(message = "O campo não pode ser vazio ou em branco")
    @Size(max = 250, message = "O campo não pode ter mais de 250 caracteres")
    @Column(name = "descricao", length = 250, nullable = false)
    private String descricao;

    @Column(name = "data_envio", nullable = false, updatable = false)
    private final LocalDate dataEnvio = LocalDate.now();

    @NotNull(message = "O campo não pode ser nulo")
    @NotBlank(message = "O campo não pode ser vazio ou em branco")
    @Size(max = 25, message = "O campo não pode ter mais de 25 caracteres")
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 25, nullable = false)
    private StatusProposta statusProposta;

    public Proposta(){}
    public Proposta(Usuario usuario, Prefeitura prefeitura, BigDecimal valor,
                    String descricao, StatusProposta statusProposta){
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Prefeitura getPrefeitura() {
        return prefeitura;
    }

    public void setPrefeitura(Prefeitura prefeitura) {
        this.prefeitura = prefeitura;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataEnvio() {
        return dataEnvio;
    }

    public StatusProposta getStatusProposta() {
        return statusProposta;
    }

    public void setStatusProposta(StatusProposta statusProposta) {
        this.statusProposta = statusProposta;
    }
}
