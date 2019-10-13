package com.jorismar.cdtapideveval.api.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "lancamento")
public class Lancamento implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long codigo;
    private Cartao cartao;
    private String beneficiario;
    private Double valor;
    private Date data_lancamento;

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

    @Column(name = "data_compra", nullable = false)
    public Date getDataLancamento() {
        return data_lancamento;
    }

    public void setDataLancamento(Date data) {
        this.data_lancamento = data;
    }

    @Override
    public String toString() {
        return "Lancamento [codigo=" + this.codigo + ", beneficiario=" + beneficiario + ", valor=" + this.valor + ", data_compra=" + this.data_lancamento
        + ", cartao=" + this.cartao.getNumero() + "]";
    }
}