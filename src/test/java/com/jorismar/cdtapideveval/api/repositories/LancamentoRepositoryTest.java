package com.jorismar.cdtapideveval.api.repositories;

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
import com.jorismar.cdtapideveval.api.entities.Lancamento;
import com.jorismar.cdtapideveval.api.entities.Portador;

@SpringBootTest
@ActiveProfiles("test")
public class LancamentoRepositoryTest {
    @Autowired
    private PortadorRepository portadorRepository;

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private LancamentoRepository lancamentoRepository;

    private static final Long LANC_CODE = 2908L;
    private static final String CARD_NUM_1 = "5988556523566001";
    private static final String CARD_NUM_2 = "5300223645785001";

    @BeforeEach
    public void setup() {
        Portador portador = createPortador();
        
        Cartao cartao1 = createCartao(CARD_NUM_1, portador);
        Cartao cartao2 = createCartao(CARD_NUM_2, portador);

        createLancamento(1111L, cartao1);
        createLancamento(2222L, cartao1);
        createLancamento(LANC_CODE, cartao2);
    }

    @AfterEach
    public void release() {
        this.lancamentoRepository.deleteAll();
        this.cartaoRepository.deleteAll();
        this.portadorRepository.deleteAll();
    }

    @Test
    public void testFindByCodigo() {
        Lancamento lancamento = this.lancamentoRepository.findByCodigo(LANC_CODE);
        assertEquals(LANC_CODE, lancamento.getCodigo());
    }

    @Test
    public void testFindByCartaoNumero() {
        List<Lancamento> list = this.lancamentoRepository.findByCartaoNumero(CARD_NUM_1);
        assertEquals(2, list.size());
    }

    @Test
    public void testFindByCartaoNumeroPageable() {
        PageRequest request = PageRequest.of(0, 10, Sort.by("codigo").ascending());
        Page<Lancamento> list = this.lancamentoRepository.findByCartaoNumero(CARD_NUM_1, request);
        assertEquals(2, list.getTotalElements());
    }

    public Portador createPortador() {
        Portador portador = new Portador();

        LocalDate birthDate = LocalDate.of(2019, 12, 31);

        portador.setCpf("12345678900");
        portador.setNome("Jorismar Barbosa");
        portador.setEmail("jorismar.barbosa@gmail.com");
        portador.setDataDeNascimento(birthDate);

        this.portadorRepository.save(portador);

        return portador;
    }

    public Cartao createCartao(String numero, Portador portador) {
        Cartao cartao = new Cartao();

        LocalDate expireDate = LocalDate.of(2019, 9, 30);

        cartao.setNumero(numero);
        cartao.setNomeDoPortador("JORISMAR B MEIRA");
        cartao.setValidade(expireDate);
        cartao.setCvc("418");
        cartao.setPortador(portador);
        cartao.setSenha("123456");

        this.cartaoRepository.save(cartao);

        return cartao;
    }

    public void createLancamento(Long codigo, Cartao cartao) {
        Lancamento lancamento = new Lancamento();

        LocalDate payDate = LocalDate.of(2019, 12, 05);

        lancamento.setCodigo(codigo);
        lancamento.setCartao(cartao);
        lancamento.setBeneficiario("Cdt*Payment");
        lancamento.setDataLancamento(payDate);
        lancamento.setValor(4000.0);

        this.lancamentoRepository.save(lancamento);
    }
}