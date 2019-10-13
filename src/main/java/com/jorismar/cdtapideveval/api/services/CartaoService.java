package com.jorismar.cdtapideveval.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.jorismar.cdtapideveval.api.entities.Cartao;

public interface CartaoService {
    Optional<Cartao> findByNumero(String numero);
    List<Cartao> findByCpf(String cpf);
    Page<Cartao> findByCpf(String cpf, PageRequest pageRequest);
    Cartao persist(Cartao cartao);
}