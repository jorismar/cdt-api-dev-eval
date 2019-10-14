package com.jorismar.cdtapideveval.api.entities;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "portador", schema = "public")
public class Portador implements Serializable {

    private static final long serialVersionUID = 1L;

    private String cpf;
    private String nome;
    private String email;
    private LocalDate dataNascimento;

    public Portador() { };

    @Id
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Column(name = "nome", nullable = false)
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Column(name = "email", nullable = false)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "data_nascimento", nullable = false)
    public LocalDate getDataDeNascimento() {
        return dataNascimento;
    }

    public void setDataDeNascimento(LocalDate dataDeNascimento) {
        this.dataNascimento = dataDeNascimento;
    }

    @Override
    public String toString() {
        return "Portador [cpf=" + this.cpf + ", nome=" + this.nome + ", email=" + this.email + ", data_de_nascimento=" + this.dataNascimento + "]";
    }
}