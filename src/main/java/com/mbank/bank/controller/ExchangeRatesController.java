package com.mbank.bank.controller;

import com.mbank.bank.domain.Rates;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Currency;
import java.util.Locale;

@RestController
@RequestMapping("/exchange")
public class ExchangeRatesController {

    @GetMapping
    public Rates getAllRates() {
        Rates rates = new Rates(Currency.getInstance(Locale.getDefault()), Currency.getInstance("EUR"));
        return rates;
    }
}
