package com.jorismar.cdtapideveval.api.utils;

import java.util.Random;

public class LancamentoUtilities {
    public static Long generateCode() {
        Long code = new Random().nextLong();
        return code < 0 ? code * -1 : code;
    }
}