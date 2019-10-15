package com.jorismar.cdtapideveval.api.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import com.jorismar.cdtapideveval.api.entities.Fatura;
import com.jorismar.cdtapideveval.api.enums.CondicaoFaturaEnum;

public class FaturaUtilities {
    public static String generatePaymentCode(String numeroCartao, Double valor, LocalDate vencimento) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("MD5");
        
        // Information to generate the MD5 Hash (Current Date and Time, Card number used in thes payment and the value)
        String info = LocalDateTime.now().toString() + numeroCartao + numeroCartao + vencimento.toString();

        digest.update(info.getBytes(), 0, info.length());

        return new BigInteger(1, digest.digest()).toString(8);
    }

    public static Double calculatePenaltyValue(Fatura fatura) {
        Double penaltyRatio = 0.004;
        long daysLate = 0L;

        // Checks if the invoice was paid after the due date.
        if (fatura.getCondicao() == CondicaoFaturaEnum.FATURADA) {
            daysLate = ChronoUnit.DAYS.between(fatura.getVencimento(), fatura.getPagamentoData());
        // Checks if the invoice has not been paid yet
        } else if (fatura.getCondicao() == CondicaoFaturaEnum.PENDENTE) {
            daysLate = ChronoUnit.DAYS.between(fatura.getVencimento(), LocalDate.now());
        }

        if (daysLate < 0) {
            daysLate = 0;
        }

        return fatura.getValor() * daysLate * penaltyRatio;
    }
}