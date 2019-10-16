package com.jorismar.cdtapideveval.api.services.impl;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.jorismar.cdtapideveval.api.entities.Reembolso;
import com.jorismar.cdtapideveval.api.repositories.ReembolsoRepository;
import com.jorismar.cdtapideveval.api.services.ReembolsoService;

@Service
public class ReembolsoServiceImpl implements ReembolsoService {
    @Autowired
    private ReembolsoRepository repository;
    private Logger logger = Logger.getLogger(ReembolsoServiceImpl.class.getName());

    @Override
    public Optional<Reembolso> findByIdentificador(String identificador) {
        logger.log(Level.FINE, "Finding a Reembolso by identificador {}...", identificador);
        return Optional.ofNullable(this.repository.findByIdentificador(identificador));
    }
    
    @Override
    public Optional<Reembolso> findByLancamentoIdentificador(String lancamento_identificador) {
        logger.log(Level.FINE, "Finding a Reembolso by lancamento_id {}...", lancamento_identificador);
        return Optional.ofNullable(this.repository.findByLancamentoIdentificador(lancamento_identificador));
    }
    
    @Override
    public Reembolso persist(Reembolso Reembolso) {
        logger.log(Level.FINE, "Storing the Reembolso '{}' in database...", Reembolso.getIdentificador());
        return this.repository.save(Reembolso);
    }
}