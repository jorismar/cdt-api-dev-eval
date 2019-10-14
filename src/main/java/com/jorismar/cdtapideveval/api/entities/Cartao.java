package com.jorismar.cdtapideveval.api.entities;

import java.io.Serializable;
import java.util.List;
import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "cartao", schema = "public")
public class Cartao implements Serializable {

    private static final long serialVersionUID = 1L;

    private String numero;
    private String nomePortador;
    private String cvc;
    private String senha;
    private LocalDate validade;
    private Portador portador;
    private List<Lancamento> lancamentos;
    private List<Fatura> faturas;

    public Cartao() {

    }

    @Id
    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    @Column(name = "nome_portador", nullable = false)
    public String getNomeDoPortador() {
        return nomePortador;
    }

    public void setNomeDoPortador(String nome) {
        this.nomePortador = nome;
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

    @Column(name = "senha", nullable = false)
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public String toString() {
        return "Cartao [numero=" + this.numero + ", nome_portador=" + this.nomePortador + ", cvc=" + this.cvc
                + ", validade=" + this.validade + ", portador=" + this.portador.getCpf() + ", senha=" + this.senha + "]";
    }

    @OneToMany(mappedBy = "cartao", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<Fatura> getFaturas() {
        return faturas;
    }

    public void setFaturas(List<Fatura> faturas) {
        this.faturas = faturas;
    }
}