package se.callistaenterprise.demoproject.camel_microservices_b.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import se.callistaenterprise.demoproject.camel_microservices_b.model.CurrencyExchange;

import java.math.BigDecimal;

@RestController
public class CurrencyExchangeController {

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange getCurrencyExchangeRate(
        @PathVariable String from,
        @PathVariable String to) {
        return new CurrencyExchange(1000L, from, to, BigDecimal.TEN);
    }
}
