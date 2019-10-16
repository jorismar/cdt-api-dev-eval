package com.jorismar.cdtapideveval.api.components;

import java.util.Optional;

import com.jorismar.cdtapideveval.api.entities.Cartao;
import com.jorismar.cdtapideveval.api.entities.Lancamento;
import com.jorismar.cdtapideveval.api.entities.Portador;
import com.jorismar.cdtapideveval.api.enums.CondicaoCartaoEnum;
import com.jorismar.cdtapideveval.api.form.Proposta;
import com.jorismar.cdtapideveval.api.services.CartaoService;
import com.jorismar.cdtapideveval.api.services.LancamentoService;
import com.jorismar.cdtapideveval.api.services.PortadorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQReceiver {
    @Autowired
    private PortadorService portadorService;

    @Autowired
    private CartaoService cartaoService;

    @Autowired
    private LancamentoService lancamentoService;
    
    public void receiveMessage(Proposta proposta) {
        Portador portador;
        
        Optional<Portador> optPortador = this.portadorService.findByCpf(proposta.getCpf());
        
        if (!optPortador.isPresent()) {
            portador = new Portador();

            portador.setCpf(proposta.getCpf());
            portador.setNome(proposta.getNome());
            portador.setEmail(proposta.getEmail());
            portador.setDataNascimento(proposta.getDataNascimento());
            portador.setRenda(proposta.getRenda());

            this.portadorService.persist(portador);
        } else {
            portador = optPortador.get();
        }
        
        Cartao cartao = new Cartao();

        cartao.setNumero(proposta.getNumeroCartao());
        cartao.setNomePortador(proposta.getNomeCartao());
        cartao.setValidade(proposta.getValidadeCartao());
        cartao.setCvc(proposta.getCvcCartao());
        cartao.setSenha(proposta.getSenhaCartao());
        cartao.setLimite(proposta.getLimiteCartao());
        cartao.setCondicao(CondicaoCartaoEnum.ATIVO);
        cartao.setPortador(portador);

        this.cartaoService.persist(cartao);
    }

    public void receiveMessage(Lancamento lancamento) {
        this.lancamentoService.persist(lancamento);
    }
}