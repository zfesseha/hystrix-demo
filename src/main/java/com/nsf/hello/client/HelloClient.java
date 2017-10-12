package com.nsf.hello.client;

import com.nsf.hello.dto.Greeting;
import org.springframework.web.client.RestTemplate;

public class HelloClient {

    private String baseUrl;

    private static final String HELLO_ROUTE = "/api/v1/hello";
    private static final RestTemplate REST_TEMPLATE = new RestTemplate();

    public HelloClient(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public Greeting getGreeting() {
        return REST_TEMPLATE.getForObject(this.baseUrl + HELLO_ROUTE, Greeting.class);
    }
}
