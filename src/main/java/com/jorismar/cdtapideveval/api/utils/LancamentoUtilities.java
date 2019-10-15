package com.jorismar.cdtapideveval.api.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

import com.jorismar.cdtapideveval.api.dtos.RegisterLancamentoDto;

public class LancamentoUtilities {
    public static String generateID(RegisterLancamentoDto dto) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("MD5");
        
        // Information to generate the MD5 Hash (Current Date and Time, Card number used in thes payment and the value)
        String info = LocalDateTime.now().toString() + dto.getCartaoNumero() + dto.getValor();

        digest.update(info.getBytes(), 0, info.length());

        return new BigInteger(1, digest.digest()).toString(16);
    }
}