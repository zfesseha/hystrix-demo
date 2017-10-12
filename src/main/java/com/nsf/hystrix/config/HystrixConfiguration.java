package com.nsf.hystrix.config;

import com.nsf.hello.client.HelloClient;
import com.nsf.hystrix.command.HelloCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HystrixConfiguration {

    @Autowired
    private HelloClient helloClient;

    @Bean
    public HelloCommand helloCommand() {
        return new HelloCommand(helloClient);
    }

}
