package com.jorismar.cdtapideveval.api.entities;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.jorismar.cdtapideveval.api.enums.CondicaoFaturaEnum;

@Entity
@Table(name = "fatura", schema = "public")
public class Fatura implements Serializable {

    private static final long serialVersionUID = 1L;

    private String codigoBarras;
    private LocalDate vencimento;
    private Double valor;
    private CondicaoFaturaEnum condicao;
    private LocalDate dataPagamento;
    private Cartao cartao;

    public Fatura() {
        // Empty
    }

    @Id
    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    @Column(name = "vencimento", nullable = false)
    public LocalDate getVencimento() {
        return vencimento;
    }

    public void setVencimento(LocalDate vencimento) {
        this.vencimento = vencimento;
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
    public CondicaoFaturaEnum getCondicao() {
        return condicao;
    }

    public void setCondicao(CondicaoFaturaEnum condicao) {
        this.condicao = condicao;
    }

    @Column(name = "data_pagamento", nullable = true)
    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDate pagamentoData) {
        this.dataPagamento = pagamentoData;
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
        return "Fatura [codigo=" + this.codigoBarras + ", vencimento=" + this.vencimento + 
            ", valor=" + this.valor + ", condicao=" + this.condicao.name() + 
            ", numero_cartao=" + this.cartao.getNumero() + "]";
    }
}