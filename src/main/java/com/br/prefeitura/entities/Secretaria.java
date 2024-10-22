package com.br.prefeitura.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;


@Entity
public class Secretaria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O campo não pode ser nulo")
    @ManyToOne
    @JoinColumn(name = "prefeitura_id", nullable = false)
    private Prefeitura prefeitura;


    @OneToMany(mappedBy = "secretaria", cascade = CascadeType.ALL)
    private List<Usuario> usuarios = new ArrayList<>();

    @OneToMany(mappedBy = "secretaria")
    private List<Obra> obras = new ArrayList<>();

    @NotNull(message = "O campo não pode ser nulo")
    @NotBlank(message = "O campo não pode ser vazio ou em branco")
    @Size(max = 100, message = "O campo não pode ter mais de 100 caracteres")
    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    @NotNull(message = "O campo não pode ser nulo")
    @NotBlank(message = "O campo não pode ser vazio ou em branco")
    @Email
    @Size(max = 100, message = "O campo não pode ter mais de 100 caracteres")
    @Column(name = "email", length = 100, nullable = false)
    private String email;

    @NotNull(message = "O campo não pode ser nulo")
    @NotBlank(message = "O campo não pode ser vazio ou em branco")
    @Size(max = 540, message = "O campo não pode ter mais de 540 caracteres")
    @Column(name = "senha", length = 540, nullable = false)
    private String senha;

    public Secretaria(){}
    public Secretaria(Prefeitura prefeitura, String nome, String email, String senha){
        this.prefeitura = prefeitura;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Prefeitura getPrefeitura() {
        return prefeitura;
    }

    public void setPrefeitura(Prefeitura prefeitura) {
        this.prefeitura = prefeitura;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public List<Obra> getObras() {
        return obras;
    }

    public void setObras(List<Obra> obras) {
        this.obras = obras;
    }
}
