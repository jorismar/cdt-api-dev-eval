package com.jorismar.cdtapideveval.api.services;

import java.util.Optional;

import com.jorismar.cdtapideveval.api.entities.Reembolso;

public interface ReembolsoService {
    Optional<Reembolso> findByIdentificador(String identificador);
    Optional<Reembolso> findByLancamentoIdentificador(String lancamento_identificador);
    Reembolso persist(Reembolso reembolso);
}