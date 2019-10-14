package com.jorismar.cdtapideveval.api.repositories;

import java.util.List;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.jorismar.cdtapideveval.api.entities.Fatura;

@Transactional(readOnly = true)

@NamedQueries({ @NamedQuery(name = "FaturaRepository.findByCartaoNumero",
        query = "SELECT fatura FROM fatura WHERE fatura.cartao_numero = :cartaoNumero") })

public interface FaturaRepository extends JpaRepository<Fatura, String> {
    Fatura findByCodigoBarras(String codigo);
    List<Fatura> findByCartaoNumero(@Param("cartaoNumero") String cartaoNumero);
    Page<Fatura> findByCartaoNumero(@Param("cartaoNumero") String cartaoNumero, Pageable pageable);
}