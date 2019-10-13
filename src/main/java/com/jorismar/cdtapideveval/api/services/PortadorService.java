package com.jorismar.cdtapideveval.api.services;

import java.util.Optional;

import com.jorismar.cdtapideveval.api.entities.Portador;

public interface PortadorService {
    Optional<Portador> findByCpf(String cpf);
    Optional<Portador> findByEmail(String cpf);
    Portador persist(Portador portador);
}