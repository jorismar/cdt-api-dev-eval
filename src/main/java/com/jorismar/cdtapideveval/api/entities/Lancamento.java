package com.jorismar.cdtapideveval.api.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "lancamento", schema = "public")
public class Lancamento implements Serializable {

    private static final long serialVersionUID = 1L;

    private String identificador;
    private LocalDateTime dataLancamento;
    private String beneficiario;
    private Double valor;
    private Cartao cartao;

    public Lancamento() {

    }
    
    @Id
    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    @Column(name = "data_lancamento", nullable = false)
    public LocalDateTime getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(LocalDateTime data) {
        this.dataLancamento = data;
    }

    @Column(name = "beneficiario", nullable = false)
    public String getBeneficiario() {
        return beneficiario;
    }

    public void setBeneficiario(String beneficiario) {
        this.beneficiario = beneficiario;
    }

    @Column(name = "valor", nullable = false)
    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    public Cartao getCartao() {
        return cartao;
    }

    public void setCartao(Cartao cartao) {
        this.cartao = cartao;
    }

    @Override
    public String toString() {
        return "Lancamento [identificador=" + this.identificador + ", beneficiario=" + beneficiario + ", valor=" + this.valor + ", data_compra=" + this.dataLancamento
        + ", cartao=" + this.cartao.getNumero() + "]";
    }
}