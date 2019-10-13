package com.jorismar.cdtapideveval.api.services.impl;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.jorismar.cdtapideveval.api.entities.Portador;
import com.jorismar.cdtapideveval.api.repositories.PortadorRepository;
import com.jorismar.cdtapideveval.api.services.PortadorService;

@Service
public class PortadorServiceImpl implements PortadorService {
    @Autowired
    private PortadorRepository repository;
    private Logger logger = Logger.getLogger(PortadorServiceImpl.class.getName());

    @Override
    public Optional<Portador> findByCpf(String cpf) {
        logger.log(Level.FINE, "Finding a Portador by CPF {}...", cpf);
        return Optional.ofNullable(this.repository.findByCpf(cpf));
    }
    
    @Override
    public Optional<Portador> findByEmail(String email) {
        logger.log(Level.FINE, "Finding a Portador by email {}...", email);
        return Optional.ofNullable(this.repository.findByEmail(email));
    }
    
    @Override
    public Portador persist(Portador portador) {
        logger.log(Level.FINE, "Storing the Portador '{}' in database...", portador.getNome());
        return this.repository.save(portador);
    }
}