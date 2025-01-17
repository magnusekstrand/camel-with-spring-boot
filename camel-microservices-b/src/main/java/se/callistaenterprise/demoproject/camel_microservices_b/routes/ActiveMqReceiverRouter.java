package se.callistaenterprise.demoproject.camel_microservices_b.routes;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.callistaenterprise.demoproject.camel_microservices_b.model.CurrencyExchange;

import java.math.BigDecimal;

@Component
public class ActiveMqReceiverRouter  extends RouteBuilder {

    @Autowired
    private MyCurrencyExchangeTransformer myCurrencyExchangeTransformer;

    @Autowired
    private MyCurrencyExchangeProcessor myCurrencyExchangeProcessor;

    @Override
    public void configure() throws Exception {
        //queue
//        from("activemq:my-activemq-queue")
//            .unmarshal().json(JsonLibrary.Jackson, CurrencyExchange.class)
//            .bean(myCurrencyExchangeProcessor)
//            .bean(myCurrencyExchangeTransformer)
//            .to("log:received-message-from-activemq");

        from("activemq:my-split-queue")
            .to("log:received-message-from-activemq");

    }

    @Component
    @Slf4j
    static class MyCurrencyExchangeProcessor {

        public void process(CurrencyExchange currencyExchange) {
            log.info("Do some processing with currencyExchange.getConversionMultiple(): {}", currencyExchange.getConversionMultiple());
        }
    }

    @Component
    @Slf4j
    static class MyCurrencyExchangeTransformer {

        public CurrencyExchange process(CurrencyExchange currencyExchange) {
            currencyExchange.setConversionMultiple(currencyExchange.getConversionMultiple().multiply(BigDecimal.TEN));
            return currencyExchange;
        }
    }

}

