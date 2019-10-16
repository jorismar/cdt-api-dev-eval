package com.jorismar.cdtapideveval.api.form;

import java.io.Serializable;
import java.time.LocalDate;

import com.jorismar.cdtapideveval.api.entities.Cartao;
import com.jorismar.cdtapideveval.api.entities.Portador;

public class Proposta implements Serializable {
    private static final long serialVersionUID = 7378703361818581836L;
    
    private String cpf;
    private String nome;
    private String email;
    private LocalDate dataNascimento;
    private Double renda;
    private String numeroCartao;
    private String nomeCartao;
    private LocalDate validadeCartao;
    private String cvcCartao;
    private String senhaCartao;
    private Double limiteCartao;
    
    public Proposta() {

    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Double getRenda() {
        return renda;
    }

    public void setRenda(Double renda) {
        this.renda = renda;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public String getNomeCartao() {
        return nomeCartao;
    }

    public void setNomeCartao(String nomeCartao) {
        this.nomeCartao = nomeCartao;
    }

    public LocalDate getValidadeCartao() {
        return validadeCartao;
    }

    public void setValidadeCartao(LocalDate validadeCartao) {
        this.validadeCartao = validadeCartao;
    }

    public String getCvcCartao() {
        return cvcCartao;
    }

    public void setCvcCartao(String cvcCartao) {
        this.cvcCartao = cvcCartao;
    }

    public String getSenhaCartao() {
        return senhaCartao;
    }

    public void setSenhaCartao(String senhaCartao) {
        this.senhaCartao = senhaCartao;
    }

    public Double getLimiteCartao() {
        return limiteCartao;
    }

    public void setLimiteCartao(Double limiteCartao) {
        this.limiteCartao = limiteCartao;
    }

    public void fill(Cartao cartao) {
        this.setNumeroCartao(cartao.getNumero());
        this.setNomeCartao(cartao.getNomePortador());
        this.setCvcCartao(cartao.getCvc());
        this.setValidadeCartao(cartao.getValidade());
        this.setLimiteCartao(cartao.getLimite());
        this.setSenhaCartao(cartao.getSenha());
    }

    public void fill(Portador portador) {
        this.setCpf(portador.getCpf());
        this.setNome(portador.getNome());
        this.setEmail(portador.getEmail());
        this.setDataNascimento(portador.getDataNascimento());
        this.setRenda(portador.getRenda());
    }

    public void fill(Portador portador, Cartao cartao) {
        this.setCpf(portador.getCpf());
        this.setNome(portador.getNome());
        this.setEmail(portador.getEmail());
        this.setDataNascimento(portador.getDataNascimento());
        this.setRenda(portador.getRenda());
        this.setNumeroCartao(cartao.getNumero());
        this.setNomeCartao(cartao.getNomePortador());
        this.setCvcCartao(cartao.getCvc());
        this.setValidadeCartao(cartao.getValidade());
        this.setLimiteCartao(cartao.getLimite());
        this.setSenhaCartao(cartao.getSenha());
    }
}