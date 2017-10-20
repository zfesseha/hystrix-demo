package com.demo.hello.client;

import com.demo.hello.dto.Greeting;
import org.springframework.web.client.RestTemplate;

public class HelloClient {

    private String baseUrl;

    private static final RestTemplate REST_TEMPLATE = new RestTemplate();

    private static final String HELLO_ROUTE = "/api/v1/greeting";
    private static final String TRIPLE_ROUTE = "/api/v1/triple/";
    private static final String TRIPLE_ONCE_ROUTE = "/api/v1/triple-once/";
    private static final String TRIPLE_WAIT_ROUTE = "/api/v1/triple-wait/";

    public HelloClient(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public Greeting greeting() {
        return REST_TEMPLATE.getForObject(this.baseUrl + HELLO_ROUTE, Greeting.class);
    }

    public Integer triple(Integer number) {
        return REST_TEMPLATE.getForObject(this.baseUrl + TRIPLE_ROUTE + number, Integer.class);
    }

    public Integer tripleOnce(Integer number) {
        return REST_TEMPLATE.getForObject(this.baseUrl + TRIPLE_ONCE_ROUTE + number, Integer.class);
    }

    public Integer tripleWait(Integer number) {
        return REST_TEMPLATE.getForObject(this.baseUrl + TRIPLE_WAIT_ROUTE + number, Integer.class);
    }
}
