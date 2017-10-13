package com.nsf.hystrix.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.nsf.hello.client.HelloClient;
import com.nsf.hello.dto.Greeting;

public class HelloCommand extends HystrixCommand<Greeting> {

    private HelloClient helloClient;

    public HelloCommand(HelloClient client) {
        super(HystrixCommandGroupKey.Factory.asKey("HelloGroup"));
        this.helloClient = client;
    }

    @Override
    protected Greeting run() throws Exception {
        return helloClient.greeting();
    }

    @Override
    protected Greeting getFallback() {
        return new Greeting("Hello", "World");
    }
}
