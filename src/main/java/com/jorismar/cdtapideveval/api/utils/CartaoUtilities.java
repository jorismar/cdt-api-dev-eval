package com.jorismar.cdtapideveval.api.utils;

import java.util.Random;
import java.util.stream.IntStream;

import java.time.LocalDate;

import com.jorismar.cdtapideveval.api.entities.Cartao;
import com.jorismar.cdtapideveval.api.entities.Portador;

public class CartaoUtilities {
    public static Cartao generate(Portador portador, String senha) {
        String number = generateNumber();
        String ownerName = generateOwnerNameAlias(portador.getNome());
        LocalDate expirationDate = generateExpirationDate();
        String cvc = generateCVC();

        Cartao cartao = new Cartao();

        cartao.setNumero(number);
        cartao.setNomeDoPortador(ownerName);
        cartao.setValidade(expirationDate);
        cartao.setCvc(cvc);
        cartao.setPortador(portador);
        cartao.setSenha(senha);

        return cartao;
    }

    private static String generateNumber() {
        final StringBuilder builder = new StringBuilder();

        // Insert card number IIN
        builder.append("51");

        // Generates the 14 left digits of card number
        Random rand = new Random();
        IntStream digits = rand.ints(14, 0, 9);
        digits.forEach(val -> builder.append(val));

        return builder.toString();
    }

    private static String generateOwnerNameAlias(String name) {
        String[] words = name.split(" ");

        if (words.length > 1) {
            words[1] = "" + words[1].charAt(0);
        }

        return String.join(" ", words).toUpperCase();
    }

    private static String generateCVC() {
        final StringBuilder builder = new StringBuilder();

        Random rand = new Random();
        IntStream digits = rand.ints(3, 0, 9);
        digits.forEach(val -> builder.append(val));

        return builder.toString();
    }

    private static LocalDate generateExpirationDate() {
        LocalDate currentDate = LocalDate.now();

        int validYears = 8;
        int expirationDay = currentDate.lengthOfMonth();
        int expirationMonth = currentDate.getMonth().getValue();
        int expirationYear = currentDate.getYear() + validYears;

        LocalDate expirationDate = LocalDate.of(expirationYear, expirationMonth, expirationDay);

        return expirationDate;
    }

    public static boolean validPassword(String password) {
        return password.length() == 6 || password.matches("^[0-9]+$");
    }
}