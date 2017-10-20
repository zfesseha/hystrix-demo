package com.demo.hystrix.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.hello.client.HelloClient;
import com.demo.hello.dto.Greeting;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class GreetingService {
	
    @Autowired
    private HelloClient helloClient;
    
    @HystrixCommand(fallbackMethod = "staticGreeting")
    public Greeting greeting() {
        return helloClient.greeting();
    }

    private Greeting staticGreeting() {
        return new Greeting("Hello", "World");
    }
}
