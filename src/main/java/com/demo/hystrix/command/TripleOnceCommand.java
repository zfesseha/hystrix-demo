package com.demo.hystrix.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.demo.hello.client.HelloClient;

public class TripleOnceCommand extends HystrixCommand<Integer> {

    private HelloClient helloClient;
    private Integer number;

    public TripleOnceCommand(HelloClient client, Integer number) {
    		// TODO: Set a separate thread pool under the same group
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
