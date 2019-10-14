package com.jorismar.cdtapideveval.api.dtos;

import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

public class RegisterLancamentoDto {
    private String cartaoNumero;
    private String portadorNome;
    private String cvc;
    private LocalDate vencimento;
    private String portadorCpf;
    private String beneficiario;
    private Double valor;
    private LocalDate dataLancamento;
    private Long codigo;

    public RegisterLancamentoDto() {

    }

    @Positive(message = "Operation code cannot be negative")
    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    @NotEmpty(message = "The payee name cannot be empty.")
    public String getBeneficiario() {
        return beneficiario;
    }

    public void setBeneficiario(String beneficiario) {
        this.beneficiario = beneficiario;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public LocalDate getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(LocalDate dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    @NotEmpty(message = "The credit cart number cannot be empty.")
    // @CreditCardNumber(message = "Invalid credit card number")
    public String getCartaoNumero() {
        return cartaoNumero;
    }

    public void setCartaoNumero(String cartaoNumero) {
        this.cartaoNumero = cartaoNumero;
    }

    @Override
    public String toString() {
        return "LancamentoDto [payment_code=" + this.codigo + ", payee=" + beneficiario + ", payment_value=" + this.valor + ", payment_date=" + this.dataLancamento
        + ", cartao=" + this.cartaoNumero + "]";
    }

    @NotEmpty(message = "The owner name in the card cannot be empty.")
    public String getPortadorNome() {
        return portadorNome;
    }

    public void setPortadorNome(String portadorNome) {
        this.portadorNome = portadorNome;
    }

    @NotEmpty(message = "The owner CPF cannot be empty.")
    @CPF(message = "Invalid CPF.")
    public String getPortadorCpf() {
        return portadorCpf;
    }

    public void setPortadorCpf(String portadorCpf) {
        this.portadorCpf = portadorCpf;
    }

    @NotEmpty(message = "The CVC cannot be empty.")
    @Length(min = 3, max = 3, message = "The CVC must be have 3 numbers.")
    public String getCvc() {
        return cvc;
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
    }

    @NotNull(message = "The expiration date cannot be null.")
    @DateTimeFormat
    public LocalDate getVencimento() {
        return vencimento;
    }

    public void setVencimento(LocalDate vencimento) {
        this.vencimento = vencimento;
    }

}