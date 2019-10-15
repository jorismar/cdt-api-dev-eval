package com.jorismar.cdtapideveval.api.scheduled;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.jorismar.cdtapideveval.api.entities.Cartao;
import com.jorismar.cdtapideveval.api.entities.Fatura;
import com.jorismar.cdtapideveval.api.entities.Lancamento;
import com.jorismar.cdtapideveval.api.enums.CondicaoFaturaEnum;
import com.jorismar.cdtapideveval.api.enums.CondicaoLancamentoEnum;
import com.jorismar.cdtapideveval.api.services.CartaoService;
import com.jorismar.cdtapideveval.api.services.FaturaService;
import com.jorismar.cdtapideveval.api.services.LancamentoService;
import com.jorismar.cdtapideveval.api.utils.FaturaUtilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class FaturaGenerator {
    @Autowired
    private FaturaService faturaService;

    @Autowired
    private CartaoService cartaoService;

    @Autowired
    private LancamentoService lancamentoService;

    public FaturaGenerator() {
        // Empty
    }

    @Scheduled(cron = "0 0 0 1 * ?")
    public void execute() {
        LocalDate vencimento = LocalDate.now().plusDays(7).plusMonths(1);

        for (Cartao cartao : this.cartaoService.findAll()) {
            Double total = 0.0;
            String codigo = "";

            // Calculates values of the overdue invoices
            for (Fatura fatura : this.faturaService.findByNumeroCartao(cartao.getNumero())) {
                if (fatura.getCondicao() != CondicaoFaturaEnum.CANCELADA) {
                    // Calculates the penalty for days late
                    total += FaturaUtilities.calculatePenaltyValue(fatura);

                    // Adds the amount of the overdue invoice
                    if (fatura.getCondicao() == CondicaoFaturaEnum.PENDENTE) {
                        total += fatura.getValor();
                        fatura.setCondicao(CondicaoFaturaEnum.ATRASADA);
                        this.faturaService.persist(fatura);
                    }
                }
            }
            
            // Sum all debts
            for (Lancamento lancamento : this.lancamentoService.findByCartao(cartao.getNumero())) {
                if (lancamento.getCondicao() == CondicaoLancamentoEnum.PENDENTE) {
                    total += lancamento.getValor();
                    lancamento.setCondicao(CondicaoLancamentoEnum.FATURADO);
                    this.lancamentoService.persist(lancamento);
                }
            }
            
            // Generates payment code
            try {
                codigo = FaturaUtilities.generatePaymentCode(cartao.getNumero(), total, vencimento);
            } catch (NoSuchAlgorithmException e) { }

            Fatura newFatura = new Fatura();

            newFatura.setCartao(cartao);
            newFatura.setCodigoBarras(codigo);
            newFatura.setCondicao(CondicaoFaturaEnum.PENDENTE);
            newFatura.setValor(total);
            newFatura.setVencimento(vencimento);
    
            this.faturaService.persist(newFatura);
        }
    }
}