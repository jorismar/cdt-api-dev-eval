package com.jorismar.cdtapideveval.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.jorismar.cdtapideveval.api.entities.Lancamento;

public interface LancamentoService {
    Optional<Lancamento> findByCodigo(Long codigo);
    List<Lancamento> findByCartao(String numero);
    Page<Lancamento> findByCartao(String numero, PageRequest pageRequest);
    Lancamento persist(Lancamento lancamento);
}