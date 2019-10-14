package com.jorismar.cdtapideveval.api.dtos;

import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

public class RegisterCartaoDto {
    private String portadorCpf;
    private String senha;
    private String numero;
    private String nomePortador;
    private LocalDate validade;
    private String cvc;

    public RegisterCartaoDto() {

    }

    @NotEmpty(message = "The credit cart owner CPF cannot be empty.")
    @CPF(message = "Invalid CPF.")
    public String getPortadorCpf() {
        return portadorCpf;
    }

    public void setPortadorCpf(String portadorCpf) {
        this.portadorCpf = portadorCpf;
    }

    @NotEmpty(message = "The card password cannot be empty.")
    @Length(min = 6, max = 6, message = "The card password must be have 6 characters.")
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @CreditCardNumber(message = "Invalid credit card number")
    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNomePortador() {
        return nomePortador;
    }

    public void setNomePortador(String nomePortador) {
        this.nomePortador = nomePortador;
    }

    public LocalDate getValidade() {
        return validade;
    }

    public void setValidade(LocalDate validade) {
        this.validade = validade;
    }

    @Length(min = 3, max = 3, message = "The CVC must be have 3 numbers.")
    public String getCvc() {
        return cvc;
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
    }

    @Override
    public String toString() {
        return "Cartao [credit_card_number=" + this.numero + ", owner_name=" + this.nomePortador + ", cvc=" + this.cvc + ", expire_date=" + this.validade
        + ", owner_cpf=" + this.portadorCpf + "]";
    }
}