package com.jorismar.cdtapideveval.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.jorismar.cdtapideveval.api.entities.Lancamento;

public interface LancamentoService {
    Optional<Lancamento> findByIdentificador(String identificador);
    List<Lancamento> findByCartao(String cartaoNumero);
    Page<Lancamento> findByCartao(String cartaoNumero, PageRequest pageRequest);
    Lancamento persist(Lancamento lancamento);
}