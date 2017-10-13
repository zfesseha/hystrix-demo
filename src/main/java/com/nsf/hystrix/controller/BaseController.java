package com.nsf.hystrix.controller;

import com.nsf.hello.client.HelloClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.nsf.hello.dto.Greeting;

@RestController
@RequestMapping("/api/v1/base")
public class BaseController {

    @Autowired
    private HelloClient helloClient;

    @RequestMapping(method = RequestMethod.GET, value = "/greeting")
    public Greeting greeting() {
        return helloClient.greeting();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/triple/{number}")
    public Integer triple(@PathVariable("number") Integer number) {
        return helloClient.triple(number);
    }
}
