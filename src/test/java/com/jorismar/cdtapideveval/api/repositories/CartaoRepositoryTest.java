package com.jorismar.cdtapideveval.api.repositories;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import com.jorismar.cdtapideveval.api.entities.Cartao;
import com.jorismar.cdtapideveval.api.entities.Portador;

@SpringBootTest
@ActiveProfiles("test")
public class CartaoRepositoryTest {
    @Autowired
    private PortadorRepository portadorRepository;

    @Autowired
    private CartaoRepository cartaoRepository;

    private static final String CPF = "12345678900";
    private static final String CARD_NUM_1 = "5988556523566001";
    private static final String CARD_NUM_2 = "5300223645785001";

    @BeforeEach
    public void setup() {
        Portador portador = createPortador();
        createCartao(CARD_NUM_1, portador);
        createCartao(CARD_NUM_2, portador);
    }

    @AfterEach
    public void release() {
        this.cartaoRepository.deleteAll();
        this.portadorRepository.deleteAll();
    }

    @Test
    public void testFindByNumero() {
        Cartao cartao = this.cartaoRepository.findByNumero(CARD_NUM_1);
        assertEquals(CARD_NUM_1, cartao.getNumero());
    }

    @Test
    public void testFindByPortadorCpf() {
        List<Cartao> list = this.cartaoRepository.findByPortadorCpf(CPF);
        assertEquals(2, list.size());
    }

    @Test
    public void testFindByPortadorCpfPageable() {
        PageRequest request = PageRequest.of(0, 10, Sort.by("numero").ascending());
        Page<Cartao> list = this.cartaoRepository.findByPortadorCpf(CPF, request);
        assertEquals(2, list.getTotalElements());
    }

    public Portador createPortador() {
        Portador portador = new Portador();

        LocalDate localDate = LocalDate.of(2019, 12, 31);
        Date birthDate = Date.valueOf(localDate);

        portador.setCpf("12345678900");
        portador.setNome("Jorismar Barbosa");
        portador.setEmail("jorismar.barbosa@gmail.com");
        portador.setDataDeNascimento(birthDate);

        this.portadorRepository.save(portador);

        return portador;
    }

    public void createCartao(String numero, Portador portador) {
        Cartao cartao = new Cartao();

        LocalDate localDate = LocalDate.of(2019, 9, 30);
        Date expireDate = Date.valueOf(localDate);

        cartao.setNumero(numero);
        cartao.setNomeDoPortador("JORISMAR B MEIRA");
        cartao.setValidade(expireDate);
        cartao.setCvc(418);
        cartao.setPortador(portador);

        this.cartaoRepository.save(cartao);
    }
}