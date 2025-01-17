package se.callistaenterprise.demoproject.camel_microservices_a.routes.c;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

//@Component
public class ActiveMqJsonSenderRouter extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        // timer
//        from("timer:active-mq-timer?period=10000")
//            .transform().constant("Hello from ActiveMQ!")
//            .log("${body}")
//            .to("activemq:my-activemq-queue");
        //queue

        from("file:files/json")
            .log("${body}")
            .to("activemq:my-activemq-queue");

    }
}
