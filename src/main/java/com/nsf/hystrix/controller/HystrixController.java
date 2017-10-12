package com.nsf.hystrix.controller;

import com.nsf.hello.client.HelloClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.nsf.hello.dto.Greeting;

@RestController
@RequestMapping("/api/v1/control")
public class HystrixController {

    @Value("${hello.url}")
    private String baseUrl;

    @RequestMapping(method = RequestMethod.GET)
    public Greeting greeting() {
        HelloClient client = new HelloClient(baseUrl);
        return client.getGreeting();
    }
}
