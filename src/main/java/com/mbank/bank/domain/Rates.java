package com.mbank.bank.domain;

import org.springframework.web.client.RestTemplate;

import java.util.Currency;

public class Rates {
    private Currency A;
    private Currency B;

    public Rates(Currency a, Currency b) {
        A = a;
        B = b;
//        String url_c = "https://api.exchangerate-api.com/v4/latest/" + a.getSymbol();
//        RestTemplate restTemplate = new RestTemplate();
//        String result = restTemplate.getForObject(url_c, String.class);
//
//        System.out.println(result);
    }

    public String getRates() {
        String a = A.getDisplayName() + "[" + A.getSymbol() + "]";
        String b = B.getDisplayName() + "[" + B.getSymbol() + "]";
        return a + "\\" + b;
    }
}
