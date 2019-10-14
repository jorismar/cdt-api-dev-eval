package com.jorismar.cdtapideveval.api.repositories;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.jorismar.cdtapideveval.api.entities.Portador;

@SpringBootTest
@ActiveProfiles("test")
public class PortadorRepositoryTest {
    @Autowired
    private PortadorRepository portadorRepository;

    private static final String CPF = "12345678900";
    private static final String EMAIL = "jorismar.barbosa@gmail.com";

    @BeforeEach
    public void setup() {
        Portador portador = new Portador();

        LocalDate birthDate = LocalDate.of(2019, 12, 31);

        portador.setCpf(CPF);
        portador.setNome("Jorismar Barbosa");
        portador.setEmail(EMAIL);
        portador.setDataDeNascimento(birthDate);

        this.portadorRepository.save(portador);
    }

    @AfterEach
    public void release() {
        this.portadorRepository.deleteAll();
    }

    @Test
    public void testFindByCpf() {
        Portador portador = this.portadorRepository.findByCpf(CPF);
        assertEquals(CPF, portador.getCpf());
    }

    @Test
    public void testFindByEmail() {
        Portador portador = this.portadorRepository.findByEmail(EMAIL);
        assertEquals(EMAIL, portador.getEmail());
    }
}