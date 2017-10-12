package com.nsf.hystrix.controller;

import com.nsf.hello.client.HelloClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.nsf.hello.dto.Greeting;

@RestController
@RequestMapping("/api/v1/base")
public class BaseController {

    @Autowired
    private HelloClient helloClient;

    @RequestMapping(method = RequestMethod.GET)
    public Greeting greeting() {
        return helloClient.getGreeting();
    }
}
