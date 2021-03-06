package com.demo.hystrix.config;

import com.demo.hello.client.HelloClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HelloConfiguration {

    @Value("${hello.url}")
    private String baseUrl;

    @Bean
    public HelloClient helloClient() {
        return new HelloClient(baseUrl);
    }
}
