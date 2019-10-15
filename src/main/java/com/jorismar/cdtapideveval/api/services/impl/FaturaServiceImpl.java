package com.jorismar.cdtapideveval.api.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jorismar.cdtapideveval.api.entities.Fatura;
import com.jorismar.cdtapideveval.api.repositories.FaturaRepository;
import com.jorismar.cdtapideveval.api.services.FaturaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class FaturaServiceImpl implements FaturaService {
    @Autowired
    private FaturaRepository repository;
    private Logger logger = Logger.getLogger(FaturaServiceImpl.class.getName());

    @Override
    public Optional<Fatura> findByCodigoBarras(String codigo) {
        logger.log(Level.FINE, "Finding a Fatura by bar code {}...", codigo);
        return Optional.ofNullable(this.repository.findByCodigoBarras(codigo));
    }

    @Override
    public List<Fatura> findByNumeroCartao(String numeroCartao) {
        logger.log(Level.FINE, "Generating list of Faturas by card number {}...", numeroCartao);
        return this.repository.findByCartaoNumero(numeroCartao);
    }

    @Override
    public Page<Fatura> findByNumeroCartao(String numeroCartao, PageRequest pageRequest) {
        logger.log(Level.FINE, "Generating list of Faturas by card number {}...", numeroCartao);
        return this.repository.findByCartaoNumero(numeroCartao, pageRequest);
    }

    @Override
    public Fatura persist(Fatura fatura) {
        logger.log(Level.FINE, "Storing the Fatura '{}' in database...", fatura.getCodigoBarras());
        return this.repository.save(fatura);
    }
}