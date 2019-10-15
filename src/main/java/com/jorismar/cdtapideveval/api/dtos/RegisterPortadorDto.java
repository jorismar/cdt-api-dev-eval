package com.jorismar.cdtapideveval.api.dtos;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

public class RegisterPortadorDto {
    private String cpf;
    private String nome;
    private String email;
    private LocalDate dataNascimento;
    private Double renda;
    private String senha;
    private String numeroCartao;
    private String nomeCartao;
    private String cvcCartao;
    private LocalDate validadeCartao;

    public RegisterPortadorDto() {
        // Empty
    }

    @NotEmpty(message = "The CPF cannot be empty.")
    @CPF(message = "Invalid CPF.")
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @NotEmpty(message = "The name cannot be empty.")
    @Length(min = 3, max = 200, message = "The name must have 3-200 characters.")
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @NotEmpty(message = "The email cannot be empty.")
    @Length(min = 3, max = 200, message = "The email must have 3-200 characters.")
    @Email(message="Invalid email.")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NotNull(message = "The birth date cannot be null.")
    @DateTimeFormat
    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    @NotNull(message = "The monthly income cannot be negative.")
    @Positive
    public Double getRenda() {
        return renda;
    }

    public void setRenda(Double renda) {
        this.renda = renda;
    }

    @NotEmpty(message = "The card password cannot be empty.")
    @Length(min = 6, max = 6, message = "The card password must be have 6 characters.")
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public String getNomeCartao() {
        return nomeCartao;
    }

    public void setNomeCartao(String nomeCartao) {
        this.nomeCartao = nomeCartao;
    }

    public LocalDate getValidadeCartao() {
        return validadeCartao;
    }

    public void setValidadeCartao(LocalDate validadeCartao) {
        this.validadeCartao = validadeCartao;
    }

    public String getCvcCartao() {
        return cvcCartao;
    }

    public void setCvcCartao(String cvcCartao) {
        this.cvcCartao = cvcCartao;
    }

    @Override
    public String toString() {
        return "PortadorDto [cpf=" + this.cpf + ", nome=" + this.nome + ", email=" + this.email + ", data_de_nascimento=" + this.dataNascimento + "]";
    }
}