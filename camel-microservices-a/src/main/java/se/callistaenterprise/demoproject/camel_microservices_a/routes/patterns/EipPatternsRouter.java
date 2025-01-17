package se.callistaenterprise.demoproject.camel_microservices_a.routes.patterns;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.callistaenterprise.demoproject.camel_microservices_a.model.CurrencyExchange;

@Component
public class EipPatternsRouter extends RouteBuilder {

    @Autowired
    private DynamicRoutingBean dynamicRouterBean;

    @Override
    public void configure() throws Exception {
        // set tracing (creates a lot more logs)
        getContext().setTracing(true);

        // dead letter queue
        errorHandler(deadLetterChannel("activemq:dead-letter-queue"));

//        from("timer:multicast?period=10000")
//            .multicast()
//            .to("log:a", "log:b", "log:c");

        // split on lines
//        from("file:files/csv")
//            .unmarshal().csv()
//            .split(body())
//            .to("log:split-files");

//        from("file:files/csv")
//            .unmarshal().csv()
//            .split(body())
//            .to("activemq:my-split-queue");

          // split on each value
//        from("file:files/csv")
//            .convertBodyTo(String.class)
//            .split(body().tokenize(","))
//            .to("activemq:my-split-queue");

//        from("file:files/csv")
//            .convertBodyTo(String.class)
//            .split(method(SplitterComponent.class, "split"))
//            .to("activemq:my-split-queue");

        //Aggregate
        // Messages => Aggregate => Endpoint
        // to, 3
        from("file:files/aggregate")
            .unmarshal()
            .json(JsonLibrary.Jackson, CurrencyExchange.class)
            .aggregate(simple("${body.to}"), new ArrayListAggregationStrategy())
            .completionSize(3)
            .to("log:aggregate-json");

        // Routing slip pattern

        //String routingSlip = "direct:endpoint1, direct:endpoint3";
        //String routingSlip = "direct:endpoint1, direct:endpoint2, direct:endpoint3";

//        from("timer:routingSlip?period=10000")
//            .transform().constant("Hello from RoutingSlip!")
//            .routingSlip(simple(routingSlip));
//
//        from("direct:endpoint1")
//            .to("log:direct-endpoint1");
//
//        from("direct:endpoint2")
//            .to("log:direct-endpoint2");
//
//        from("direct:endpoint3")
//            .to("log:direct-endpoint3");

        // Dynamic routing pattern

        // Step 1, Step 2, Step 3

        // Endpoint1
        // Endpoint2
        // Endpoint3

        from("timer:routingSlip?period={{timePeriod}}")
            .transform().constant("Hello from DynamicRouting!")
            .dynamicRouter(method(dynamicRouterBean, "route"));

        // wiretapping
        from("direct:endpoint1")
            .wireTap("log:wiretap")  //add
            .to("{{endpoint-for-logging}}");

        from("direct:endpoint2")
            .to("log:direct-endpoint2");

        from("direct:endpoint3")
            .to("log:direct-endpoint3");
    }
}

