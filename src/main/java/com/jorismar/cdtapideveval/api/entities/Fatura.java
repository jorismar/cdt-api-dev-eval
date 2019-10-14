package com.jorismar.cdtapideveval.api.entities;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "fatura", schema = "public")
public class Fatura implements Serializable {

    private static final long serialVersionUID = 1L;

    private String codigoBarras;
    private String cartaoNumero;
    private LocalDate vencimento;
    private LocalDate periodoInicio;
    private LocalDate periodoTermino;
    private LocalDate pagamentoData;
    private Double valor;
    private Cartao cartao;

    public Fatura() {

    }

    @Id
    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    @Column(name = "cartao_numero", nullable = false, insertable = false, updatable = false)
    public String getCartaoNumero() {
        return cartaoNumero;
    }

    public void setCartaoNumero(String cartaoNum) {
        this.cartaoNumero = cartaoNum;
    }

    @Column(name = "vencimento", nullable = false)
    public LocalDate getVencimento() {
        return vencimento;
    }

    public void setVencimento(LocalDate vencimento) {
        this.vencimento = vencimento;
    }

    @Column(name = "periodo_inicio", nullable = false)
    public LocalDate getPeriodoInicio() {
        return periodoInicio;
    }

    public void setPeriodoInicio(LocalDate periodoInicio) {
        this.periodoInicio = periodoInicio;
    }

    @Column(name = "periodo_termino", nullable = false)
    public LocalDate getPeriodoTermino() {
        return periodoTermino;
    }

    public void setPeriodoTermino(LocalDate periodoTermino) {
        this.periodoTermino = periodoTermino;
    }

    @Column(name = "pagamento_data", nullable = true)
    public LocalDate getPagamentoData() {
        return pagamentoData;
    }

    public void setPagamentoData(LocalDate pagamentoData) {
        this.pagamentoData = pagamentoData;
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
}