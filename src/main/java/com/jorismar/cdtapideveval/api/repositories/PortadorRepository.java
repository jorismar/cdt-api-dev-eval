package com.jorismar.cdtapideveval.api.repositories;

import com.jorismar.cdtapideveval.api.entities.Portador;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface PortadorRepository extends JpaRepository<Portador, String> {
    Portador findByCpf(String cpf);
    Portador findByEmail(String email);
}