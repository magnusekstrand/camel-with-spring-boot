package se.callistaenterprise.demoproject.camel_microservices_a.routes.b;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Body;
import org.apache.camel.ExchangeProperties;
import org.apache.camel.Headers;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

//@Component
public class MyFileRouter extends RouteBuilder {

    //@Autowired
    private DeciderBean deciderBean;

    @Override
    public void configure() throws Exception {
        from("file:files/input")
            .routeId("Files-Input-Route")
            .transform().body(String.class)
            .choice() // Content Based Routing
                .when(simple("${file:ext} ends with 'xml'"))
                    .log("XML file detected")
                .when(method(deciderBean, "isValid"))
                    .log("NOT and XML file")
                .otherwise()
                    .log("Not an XML file")
            .end()
            //.to("direct://log-file-values")
            .to("file:files/output");

        from("direct:log-file-values")
            .log("${messageHistory} ${file:absolute.path}")
            .log("${file:name.ext} ${file:name.noext} ${file:onlyname}")
            .log("${file:onlyname.noext} ${file:parent} ${file:path} ${file:absolute}")
            .log("${file:size} ${file:modified}")
            .log("${routeId} ${camelId} ${body}");
    }
}

@Component
@Slf4j
class DeciderBean {
     public boolean isValid(
         @Body String body,
         @Headers Map<String, String> headers,
         @ExchangeProperties Map<String, String> exchangeProperties
     ) {
        log.info("DeciderBean.isValid: \n{}\n{}\n{}", body, headers, exchangeProperties);
         return true;
     }
}
