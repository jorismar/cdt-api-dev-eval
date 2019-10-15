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
import com.jorismar.cdtapideveval.api.enums.CondicaoCartaoEnum;
import com.jorismar.cdtapideveval.api.enums.CondicaoLancamentoEnum;

@SpringBootTest
@ActiveProfiles("test")
public class LancamentoRepositoryTest {
    @Autowired
    private PortadorRepository portadorRepository;

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private LancamentoRepository lancamentoRepository;

    private static final String LANC_CODE = "ccccccc";
    private static final String CARD_NUM_1 = "5988556523566001";
    private static final String CARD_NUM_2 = "5300223645785001";

    @BeforeEach
    public void setup() {
        Portador portador = createPortador();
        
        Cartao cartao1 = createCartao(CARD_NUM_1, portador);
        Cartao cartao2 = createCartao(CARD_NUM_2, portador);

        createLancamento("aaaaaaa", cartao1);
        createLancamento("bbbbbbb", cartao1);
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
        Lancamento lancamento = this.lancamentoRepository.findByIdentificador(LANC_CODE);
        assertEquals(LANC_CODE, lancamento.getIdentificador());
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
        portador.setDataNascimento(birthDate);
        portador.setRenda(4000.0);

        this.portadorRepository.save(portador);

        return portador;
    }

    public Cartao createCartao(String numero, Portador portador) {
        Cartao cartao = new Cartao();

        LocalDate expireDate = LocalDate.of(2019, 9, 30);

        cartao.setNumero(numero);
        cartao.setNomePortador("JORISMAR B MEIRA");
        cartao.setValidade(expireDate);
        cartao.setCvc("418");
        cartao.setPortador(portador);
        cartao.setSenha("123456");
        cartao.setLimite(5000.0);
        cartao.setCondicao(CondicaoCartaoEnum.ATIVO);

        this.cartaoRepository.save(cartao);

        return cartao;
    }

    public void createLancamento(String identificador, Cartao cartao) {
        Lancamento lancamento = new Lancamento();

        LocalDate payDate = LocalDate.of(2019, 12, 05);

        lancamento.setIdentificador(identificador);
        lancamento.setCartao(cartao);
        lancamento.setBeneficiario("Cdt*Payment");
        lancamento.setDataLancamento(payDate);
        lancamento.setValor(40.0);
        lancamento.setCondicao(CondicaoLancamentoEnum.PENDENTE);

        this.lancamentoRepository.save(lancamento);
    }
}