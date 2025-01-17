package se.callistaenterprise.demoproject.camel_microservices_a.routes.patterns;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Body;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangeProperties;
import org.apache.camel.Headers;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("dynamicRoutingBean")
@Slf4j
public class DynamicRoutingBean {

    private int step = 1;

    /**
     * This method defines the dynamic routing logic.
     * It determines the next route dynamically based on the current state or some business logic.
     */
    public String route(
        @ExchangeProperties Map<String, String> properties,
        @Headers Map<String, String> headers,
        @Body String body) {

        log.info("DynamicRoutingBean.route: \n{}\n{}\n{}", properties, headers, body);

        // Example: Route dynamically between different endpoints
        return switch (step++) {
            case 1 -> "direct:endpoint1";
            case 2 -> "direct:endpoint2";
            case 3 -> "direct:endpoint3";
            default -> {
                // Return null to indicate end of routing
                step = 1; // Reset for the next cycle
                yield null;
            }
        };
    }
}
