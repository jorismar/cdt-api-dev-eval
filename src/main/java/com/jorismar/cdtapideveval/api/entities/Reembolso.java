package com.jorismar.cdtapideveval.api.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "reembolso", schema = "public")
public class Reembolso implements Serializable {

    private static final long serialVersionUID = 1L;

    private String identificador;
    private Lancamento lancamento;
    private LocalDateTime dataReembolso;

    public Reembolso() {

    }

    @Id
    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    @Column(name = "data_reembolso", nullable = false)
    public LocalDateTime getDataReembolso() {
        return dataReembolso;
    }

    public void setDataReembolso(LocalDateTime dataReembolso) {
        this.dataReembolso = dataReembolso;
    }

    @OneToOne(fetch = FetchType.EAGER)
    public Lancamento getLancamento() {
        return lancamento;
    }

    public void setLancamento(Lancamento lancamento) {
        this.lancamento = lancamento;
    }
}