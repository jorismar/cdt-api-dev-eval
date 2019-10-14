package com.jorismar.cdtapideveval.api.repositories;

import java.util.List;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.jorismar.cdtapideveval.api.entities.Cartao;

@Transactional(readOnly = true)

@NamedQueries({ @NamedQuery(name = "CartaoRepository.findByPortadorCpf",
        query = "SELECT cartao FROM cartao WHERE cartao.portador_cpf = :portadorCpf") })

public interface CartaoRepository extends JpaRepository<Cartao, String> {
    Cartao findByNumero(String numero);
    List<Cartao> findByPortadorCpf(@Param("portadorCpf") String portadorCpf);
    Page<Cartao> findByPortadorCpf(@Param("portadorCpf") String portadorCpf, Pageable pageable);
}