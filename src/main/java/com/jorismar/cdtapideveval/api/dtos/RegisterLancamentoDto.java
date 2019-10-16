package com.jorismar.cdtapideveval.api.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.springframework.format.annotation.DateTimeFormat;

public class RegisterLancamentoDto {
    private String cartaoNumero;
    private String portadorNome;
    private LocalDate validade;
    private String cvc;
    private String portadorCpf;
    private String beneficiario;
    private Double valor;
    private String identificador;
    private LocalDateTime dataLancamento;

    public RegisterLancamentoDto() {
        // Empty
    }

    @NotEmpty(message = "The credit cart number cannot be empty.")
    // @CreditCardNumber(message = "Invalid credit card number")
    public String getCartaoNumero() {
        return cartaoNumero;
    }

    public void setCartaoNumero(String cartaoNumero) {
        this.cartaoNumero = cartaoNumero;
    }

    @NotEmpty(message = "The owner name in the card cannot be empty.")
    public String getPortadorNome() {
        return portadorNome;
    }

    public void setPortadorNome(String portadorNome) {
        this.portadorNome = portadorNome;
    }

    @NotNull(message = "The expiration date cannot be null.")
    @DateTimeFormat
    public LocalDate getValidade() {
        return validade;
    }

    public void setValidade(LocalDate validade) {
        this.validade = validade;
    }

    @NotEmpty(message = "The CVC cannot be empty.")
    @Length(min = 3, max = 3, message = "The CVC must be have 3 numbers.")
    public String getCvc() {
        return cvc;
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
    }

    @NotEmpty(message = "The owner CPF cannot be empty.")
    @CPF(message = "Invalid CPF.")
    public String getPortadorCpf() {
        return portadorCpf;
    }

    public void setPortadorCpf(String portadorCpf) {
        this.portadorCpf = portadorCpf;
    }

    @NotEmpty(message = "The payee name cannot be empty.")
    public String getBeneficiario() {
        return beneficiario;
    }

    public void setBeneficiario(String beneficiario) {
        this.beneficiario = beneficiario;
    }

    @NotNull(message = "The payment value cannot be empty.")
    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public LocalDateTime getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(LocalDateTime dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    @Override
    public String toString() {
        return "LancamentoDto [payment_code=" + this.identificador + ", payee=" + beneficiario + ", payment_value=" + this.valor + ", payment_date=" + this.dataLancamento
        + ", cartao=" + this.cartaoNumero + "]";
    }
}