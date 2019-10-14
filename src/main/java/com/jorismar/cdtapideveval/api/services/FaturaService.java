package com.jorismar.cdtapideveval.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.jorismar.cdtapideveval.api.entities.Fatura;

public interface FaturaService {
    Optional<Fatura> findByCodigoBarras(String codigo);
    List<Fatura> findByNumeroCartao(String numeroCartao);
    Page<Fatura> findByNumeroCartao(String numeroCartao, PageRequest pageRequest);
    Fatura persist(Fatura fatura);
}