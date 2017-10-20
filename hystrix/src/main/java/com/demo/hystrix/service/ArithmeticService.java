package com.demo.hystrix.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.hello.client.HelloClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Service
public class ArithmeticService {
	
	private static final String DEFAULT_TIMEOUT_THRESHOLD = "1500";
	
    @Autowired
    private HelloClient helloClient;
    
    @HystrixCommand(commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = DEFAULT_TIMEOUT_THRESHOLD)
        })
    public Integer tripleWait(Integer number) {
        return helloClient.tripleWait(number);
    }
    
    @HystrixCommand(fallbackMethod = "staticTriple", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = DEFAULT_TIMEOUT_THRESHOLD)
        })
    public Integer tripleWaitWithFallback(Integer number) {
        return helloClient.tripleWait(number);
    }

    private Integer staticTriple(Integer number) {
        return 3 * number;
    }
}
