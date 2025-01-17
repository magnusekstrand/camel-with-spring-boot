package se.callistaenterprise.demoproject.camel_microservices_a.routes.a;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
//@Component
public class MyFirstTimerRouter extends RouteBuilder {

    //@Autowired
    private GetCurrentTimeBean getCurrentTimeBean;

    //@Autowired
    private SimpleLoggingProcessorComponent simpleLoggingProcessorComponent;

    @Override
    public void configure() throws Exception {
        // timer
        // transformation
        // log
        from("timer:first-timer")
            .log("${body}")
            .transform().constant("My Constant Message")
            .log("${body}")
//            .transform().simple("The time is: "´ + LocalDateTime.now())
//            .bean("getCurrentTimeBean")
            .bean(getCurrentTimeBean, "getCurrentTime")
            .log("${body}")
            .bean(simpleLoggingProcessorComponent, "process")  // do some processing
            .log("${body}")
            .process(new SimpleLoggingProcessor())
            .to("log:first-timer");
    }
}

@Component
class GetCurrentTimeBean {
    public String getCurrentTime() {
        return "The time is: " + LocalDateTime.now();
    }
}

@Slf4j
@Component
class SimpleLoggingProcessorComponent {
    public void process(String message) {
        log.info("SimpleLoggingProcessorComponent: {}", message);
    }
}

@Slf4j
class SimpleLoggingProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        log.info("SimpleLoggingProcessor: {}", exchange.getMessage().getBody());
    }
}
