package com.jorismar.cdtapideveval.api.entities;

import java.io.Serializable;
import java.util.List;
import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "cartao")
public class Cartao implements Serializable {

    private static final long serialVersionUID = 1L;

    private String numero;
    private String nome_portador;
    private Date validade;
    private int cvc;
    private Portador portador;
    private List<Lancamento> lancamentos;

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
        return nome_portador;
    }

    public void setNomeDoPortador(String nome) {
        this.nome_portador = nome;
    }

    @Column(name = "validade", nullable = false)
    public Date getValidade() {
        return validade;
    }

    public void setValidade(Date validade) {
        this.validade = validade;
    }

    @Column(name = "cvc", nullable = false)
    public int getCvc() {
        return cvc;
    }

    public void setCvc(int cvc) {
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

    @Override
    public String toString() {
        return "Cartao [numero=" + this.numero + ", nome_portador=" + this.nome_portador + ", cvc=" + this.cvc + ", validade=" + this.validade
        + ", portador=" + this.portador.getCpf() + "]";
    }
}