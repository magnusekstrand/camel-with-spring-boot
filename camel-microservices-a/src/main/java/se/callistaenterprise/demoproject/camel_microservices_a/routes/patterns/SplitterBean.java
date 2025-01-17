package se.callistaenterprise.demoproject.camel_microservices_a.routes.patterns;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
class SplitterBean {
    public List<String> split(String body) {
        return List.of(body.split(","));
    }
}
