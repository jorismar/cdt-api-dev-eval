package com.jorismar.cdtapideveval.api.repositories;

import java.util.List;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.jorismar.cdtapideveval.api.entities.Lancamento;

@Transactional(readOnly = true)

@NamedQueries({ @NamedQuery(name = "LancamentoRepository.findByCartaoNumero",
        query = "SELECT lancamento FROM lancamento WHERE lancamento.cartao_numero = :cartaoNumero") })

public interface LancamentoRepository extends JpaRepository<Lancamento, String> {
    Lancamento findByIdentificador(String identificador);
    List<Lancamento> findByCartaoNumero(@Param("cartaoNumero") String cartaoNumero);
    Page<Lancamento> findByCartaoNumero(@Param("cartaoNumero") String cartaoNumero, Pageable pageable);
    
    @Query(value = "SELECT * FROM lancamento WHERE lancamento.identificador = reembolso.lancamento_id", nativeQuery = true)
    List<Lancamento> findAllRefunded();
}