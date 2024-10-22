package com.br.prefeitura.entities;

import com.br.prefeitura.enums.StatusObra;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.UniqueElements;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Obra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @UniqueElements(message = "O campo não pode ter valor repetido")
    @NotNull(message = "O campo não pode ser nulo")
    @OneToOne
    @JoinColumn(name = "licitacao_id", unique = true, nullable = false)
    private Licitacao licitacao;

    @NotNull(message = "O campo não pode ser nulo")
    @ManyToOne
    @JoinColumn(name = "secretaria_id", nullable = false)
    private Secretaria secretaria;

    @OneToMany(mappedBy = "obra", cascade = CascadeType.ALL)
    private List<Nota> notas = new ArrayList<>();

    @NotNull(message = "O campo não pode ser nulo")
    @OneToOne(mappedBy = "obra")
    private Contrato contrato;

    @NotNull(message = "O campo não pode ser nulo")
    @NotBlank(message = "O campo não pode ser vazio ou em branco")
    @Size(max = 250, message = "O campo não pode ter mais de 250 caracteres")
    @Column(name = "descricao", length = 250, nullable = false)
    private String descricao;

    @Column(name = "data_inicio", nullable = false, updatable = false)
    private final LocalDate dataInicio = LocalDate.now();

    @Column(name = "data_fim")
    private LocalDate dataFim;

    @NotNull(message = "O campo não pode ser nulo")
    @NotBlank(message = "O campo não pode ser vazio ou em branco")
    @Size(max = 25, message = "O campo não pode ter mais de 25 caracteres")
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 25, nullable = false)
    private StatusObra statusObra;

    public Obra(){}
    public Obra(Licitacao licitacao, Secretaria secretaria, String descricao,
                StatusObra statusObra){
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Nota> getNotas() {
        return notas;
    }

    public void setNotas(List<Nota> notas) {
        this.notas = notas;
    }

    public Licitacao getLicitacao() {
        return licitacao;
    }

    public void setLicitacao(Licitacao licitacao) {
        this.licitacao = licitacao;
    }

    public Secretaria getSecretaria() {
        return secretaria;
    }

    public void setSecretaria(Secretaria secretaria) {
        this.secretaria = secretaria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public StatusObra getStatusObra() {
        return statusObra;
    }

    public void setStatusObra(StatusObra statusObra) {
        this.statusObra = statusObra;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }
}
