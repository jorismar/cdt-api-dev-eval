package com.jorismar.cdtapideveval.api.entities;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "lancamento", schema = "public")
public class Lancamento implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long codigo;
    private Cartao cartao;
    private String beneficiario;
    private Double valor;
    private LocalDate dataLancamento;
    private Double valorCobranca;

    public Lancamento() {

    }
    
    @Id
    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    public Cartao getCartao() {
        return cartao;
    }

    public void setCartao(Cartao cartao) {
        this.cartao = cartao;
    }

    @Column(name = "estabelecimento", nullable = false)
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

    @Column(name = "data_lancamento", nullable = false)
    public LocalDate getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(LocalDate data) {
        this.dataLancamento = data;
    }

    @Override
    public String toString() {
        return "Lancamento [codigo=" + this.codigo + ", beneficiario=" + beneficiario + ", valor=" + this.valor + ", data_compra=" + this.dataLancamento
        + ", cartao=" + this.cartao.getNumero() + "]";
    }

    @Column(name = "valor_cobranca", nullable = false)
    public Double getValorCobranca() {
        return valorCobranca;
    }

    public void setValorCobranca(Double valorCobranca) {
        this.valorCobranca = valorCobranca;
    }

}