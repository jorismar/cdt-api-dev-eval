package com.jorismar.cdtapideveval.api.scheduled;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.jorismar.cdtapideveval.api.entities.Cartao;
import com.jorismar.cdtapideveval.api.entities.Fatura;
import com.jorismar.cdtapideveval.api.entities.Lancamento;
import com.jorismar.cdtapideveval.api.enums.CondicaoFaturaEnum;
import com.jorismar.cdtapideveval.api.services.CartaoService;
import com.jorismar.cdtapideveval.api.services.FaturaService;
import com.jorismar.cdtapideveval.api.services.LancamentoService;
import com.jorismar.cdtapideveval.api.services.ReembolsoService;
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

    @Autowired
    private ReembolsoService reembolsoService;

    public FaturaGenerator() {
        // Empty
    }

    @Scheduled(cron = "0 0 0 1 * ?")
    public void execute() {
        LocalDate currentDate = LocalDate.now();
        LocalDate referenceDate = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), 1);
        LocalDate beginDate = referenceDate.minusMonths(1);
        LocalDate endDate = referenceDate.minusDays(1);

        LocalDateTime beginPeriod = LocalDateTime.of(beginDate.getYear(), beginDate.getMonth(), beginDate.getDayOfMonth(), 0, 0, 0, 0);
        LocalDateTime endPeriod = LocalDateTime.of(endDate.getYear(), endDate.getMonth(), endDate.getDayOfMonth(), 23, 59, 59, 999999999);
        
        LocalDate vencimento = currentDate.plusDays(7).plusMonths(1);

        for (Cartao cartao : this.cartaoService.findAll()) {
            Double total = 0.0;
            String codigo = "";

            // Calculates values of the overdue invoices
            for (Fatura fatura : this.faturaService.findByNumeroCartao(cartao.getNumero())) {
                if (fatura.getCondicao() == CondicaoFaturaEnum.PENDENTE) {
                    // Calculates the penalty for days late
                    total += FaturaUtilities.calculatePenaltyValue(fatura);

                    // Adds the amount of the overdue invoice
                    total += fatura.getValor();
                }
            }
            
            // Sum all debts
            for (Lancamento lancamento : this.lancamentoService.findByCartao(cartao.getNumero())) {
                LocalDateTime lancDate = lancamento.getDataLancamento();
                if ((lancDate.isEqual(beginPeriod) || lancDate.isAfter(beginPeriod)) && lancDate.isBefore(endPeriod)) {
                    if (!this.reembolsoService.findByLancamentoIdentificador(lancamento.getIdentificador()).isPresent()) {
                        total += lancamento.getValor();
                    }
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