package com.jorismar.cdtapideveval.api.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jorismar.cdtapideveval.api.entities.Cartao;
import com.jorismar.cdtapideveval.api.repositories.CartaoRepository;
import com.jorismar.cdtapideveval.api.services.CartaoService;

@Service
public class CartaoServiceImpl implements CartaoService {
    @Autowired
    private CartaoRepository repository;
    private Logger logger = Logger.getLogger(CartaoServiceImpl.class.getName());

    @Override
    public Optional<Cartao> findByNumero(String numero) {
        logger.log(Level.FINE, "Finding a Cartao by number {}...", numero);
        return Optional.ofNullable(this.repository.findByNumero(numero));
    }

    @Override
    public List<Cartao> findByCpf(String cpf) {
        logger.log(Level.FINE, "Generating list of Carato by CPF {}...", cpf);
        return this.repository.findByPortadorCpf(cpf);
    }

    @Override
    public Page<Cartao> findByCpf(String cpf, PageRequest pageRequest) {
        logger.log(Level.FINE, "Generating list of Carato by CPF {}...", cpf);
        return this.repository.findByPortadorCpf(cpf, pageRequest);
    }

    @Override
    public Cartao persist(Cartao cartao) {
        logger.log(Level.FINE, "Storing the Cartao '{}' in database...", cartao.getNumero());
        return this.repository.save(cartao);
    }
}