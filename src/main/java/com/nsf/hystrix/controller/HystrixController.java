package com.nsf.hystrix.controller;

import com.nsf.hello.client.HelloClient;
import com.nsf.hello.dto.Greeting;
import com.nsf.hystrix.command.HelloCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/hystrix")
public class HystrixController {

    @Autowired
    private HelloCommand helloCommand;

    @RequestMapping(method = RequestMethod.GET)
    public Greeting greeting() {
        return helloCommand.execute();
    }
}
