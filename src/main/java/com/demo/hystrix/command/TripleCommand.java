package com.demo.hystrix.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.demo.hello.client.HelloClient;

public class TripleCommand extends HystrixCommand<Integer> {

    private HelloClient helloClient;
    private Integer number;

    public TripleCommand(HelloClient client, Integer number) {
        super(HystrixCommandGroupKey.Factory.asKey("TripleGroup"));
        this.helloClient = client;
        this.number = number;
    }

    @Override
    protected Integer run() throws Exception {
        return helloClient.triple(number);
    }

    @Override
    protected String getCacheKey() {
        return String.valueOf(number);
    }
}
