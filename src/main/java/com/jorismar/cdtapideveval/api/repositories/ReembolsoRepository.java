package com.jorismar.cdtapideveval.api.repositories;

import com.jorismar.cdtapideveval.api.entities.Reembolso;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface ReembolsoRepository extends JpaRepository<Reembolso, String> {
    Reembolso findByIdentificador(String identificador);
    Reembolso findByLancamentoIdentificador(String lancamento_identificador);
}