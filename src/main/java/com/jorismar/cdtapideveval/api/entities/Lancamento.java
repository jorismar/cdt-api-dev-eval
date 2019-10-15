package com.jorismar.cdtapideveval.api.entities;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.jorismar.cdtapideveval.api.enums.CondicaoLancamentoEnum;

@Entity
@Table(name = "lancamento", schema = "public")
public class Lancamento implements Serializable {

    private static final long serialVersionUID = 1L;

    private String identificador;
    private LocalDate dataLancamento;
    private String beneficiario;
    private Double valor;
    private CondicaoLancamentoEnum condicao;
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
    public LocalDate getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(LocalDate data) {
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

    @Enumerated(EnumType.STRING)
    @Column(name = "condicao", nullable = false)
    public CondicaoLancamentoEnum getCondicao() {
        return condicao;
    }

    public void setCondicao(CondicaoLancamentoEnum condicao) {
        this.condicao = condicao;
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