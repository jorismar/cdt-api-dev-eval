package com.jorismar.cdtapideveval.api.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.jorismar.cdtapideveval.api.enums.CondicaoCartaoEnum;

@Entity
@Table(name = "cartao", schema = "public")
public class Cartao implements Serializable {

    private static final long serialVersionUID = 1L;

    private String numero;
    private String nomePortador;
    private LocalDate validade;
    private String cvc;
    private String senha;
    private Double limite;
    private CondicaoCartaoEnum condicao;
    private Portador portador;
    private List<Lancamento> lancamentos;
    private List<Fatura> faturas;

    public Cartao() {
        // Empty
    }

    @Id
    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    @Column(name = "nome_portador", nullable = false)
    public String getNomePortador() {
        return nomePortador;
    }

    public void setNomePortador(String nomePortador) {
        this.nomePortador = nomePortador;
    }

    @Column(name = "validade", nullable = false)
    public LocalDate getValidade() {
        return validade;
    }

    public void setValidade(LocalDate validade) {
        this.validade = validade;
    }

    @Column(name = "cvc", nullable = false)
    public String getCvc() {
        return cvc;
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
    }

    @Column(name = "senha", nullable = false)
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Column(name = "limite", nullable = false)
    public Double getLimite() {
        return limite;
    }

    public void setLimite(Double limite) {
        this.limite = limite;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "condicao", nullable = false)
    public CondicaoCartaoEnum getCondicao() {
        return condicao;
    }

    public void setCondicao(CondicaoCartaoEnum condicao) {
        this.condicao = condicao;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    public Portador getPortador() {
        return portador;
    }

    public void setPortador(Portador portador) {
        this.portador = portador;
    }

    @OneToMany(mappedBy = "cartao", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<Lancamento> getLancamentos() {
        return this.lancamentos;
    }

    public void setLancamentos(List<Lancamento> lancamentos) {
        this.lancamentos = lancamentos;
    }

    @OneToMany(mappedBy = "cartao", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<Fatura> getFaturas() {
        return faturas;
    }

    public void setFaturas(List<Fatura> faturas) {
        this.faturas = faturas;
    }

    @Override
    public String toString() {
        return "Cartao [numero=" + this.numero + ", nome_portador=" + this.nomePortador + 
                ", validade=" + this.validade + ", cvc=" + this.cvc + ", senha=" + this.senha + 
                ", limite=" + this.limite + ", condicao=" + this.condicao.name() + ", portador=" + this.portador.getCpf() + "]";
    }
}