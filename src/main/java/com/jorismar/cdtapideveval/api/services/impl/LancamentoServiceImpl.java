package com.jorismar.cdtapideveval.api.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jorismar.cdtapideveval.api.entities.Lancamento;
import com.jorismar.cdtapideveval.api.repositories.LancamentoRepository;
import com.jorismar.cdtapideveval.api.services.LancamentoService;

@Service
public class LancamentoServiceImpl implements LancamentoService {
    @Autowired
    private LancamentoRepository repository;
    private Logger logger = Logger.getLogger(LancamentoServiceImpl.class.getName());

    @Override
    public Optional<Lancamento> findByCodigo(Long codigo) {
        logger.log(Level.FINE, "Finding a Lancamento by codigo {}...", codigo);
        return Optional.ofNullable(this.repository.findByCodigo(codigo));
    }

    @Override
    public List<Lancamento> findByCartao(String numero) {
        logger.log(Level.FINE, "Generating list of Lancamentos by credit card number {}...", numero);
        return this.repository.findByCartaoNumero(numero);
    }

    @Override
    public Page<Lancamento> findByCartao(String numero, PageRequest pageRequest) {
        logger.log(Level.FINE, "Generating page of Lancamentos by credit card number {}...", numero);
        return findByCartao(numero, pageRequest);
    }

    @Override
    public Lancamento persist(Lancamento lancamento) {
        logger.log(Level.FINE, "Storing the Lancamento '{}' in database...", lancamento.getCodigo());
        return this.repository.save(lancamento);
    }  
}