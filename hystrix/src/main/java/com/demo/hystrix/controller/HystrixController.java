package com.demo.hystrix.controller;

import com.demo.hystrix.command.HelloCommand;

import com.demo.hello.client.HelloClient;
import com.demo.hello.dto.Greeting;
import com.demo.hystrix.command.TripleCommand;
import com.demo.hystrix.command.TripleOnceCommand;

import com.demo.hystrix.rest.Response;
import com.demo.hystrix.service.ArithmeticService;
import com.demo.hystrix.service.GreetingService;
import com.google.common.collect.Iterables;
import com.netflix.hystrix.HystrixInvokableInfo;
import com.netflix.hystrix.HystrixRequestLog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/hystrix")
public class HystrixController {

    @Autowired
    private HelloClient helloClient;

    @Autowired
    private GreetingService greetingService;

    @Autowired
    private ArithmeticService arithmeticService;

    @RequestMapping(method = RequestMethod.GET, value = "/greeting")
    public Response<Greeting> greeting() {
        HelloCommand command = new HelloCommand(helloClient);
        return new Response<Greeting>(command.execute(), command.isResponseFromCache(), command.isResponseFromFallback());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/anotation/greeting")
    public Response<Greeting> annotationGreeting() {
        Greeting greeting = greetingService.greeting();
        HystrixInvokableInfo<?> lastCommand = getLastCommand();
		return new Response<Greeting>(greeting, lastCommand.isResponseFromCache(), lastCommand.isResponseFromFallback());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/triple/{number}")
    public Integer triple(@PathVariable("number") Integer number) {
        TripleCommand command = new TripleCommand(helloClient, number);
        return command.execute();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/triple-all")
    public Response<Integer> [] tripleAll(@RequestParam("numbers") String numbersParam) {
    		// TODO: Validate query param before sending downstream
    		String [] numbersString = numbersParam.split(",");
    		
    		// TODO: start-functional - use functional pattern for this
        Response<Integer> [] numbers = new Response[numbersString.length];
    		int index = 0;
    		for (String numString : numbersString) {
    			TripleOnceCommand command = new TripleOnceCommand(helloClient, Integer.valueOf(numString));
    			numbers[index] = new Response<Integer>(command.execute(), command.isResponseFromCache(), command.isResponseFromFallback());
    			index++;
    		}
    		// TODO: end-functional
        return numbers;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/triple-wait/{number}")
    public Response<Integer> tripleWait(@PathVariable("number") Integer number) {
        Integer result = arithmeticService.tripleWait(number);
        HystrixInvokableInfo<?> lastCommand = getLastCommand();
		return new Response<Integer>(result, lastCommand.isResponseFromCache(), lastCommand.isResponseFromFallback());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/triple-wait-fallback/{number}")
    public Response<Integer> tripleWaitWithFallback(@PathVariable("number") Integer number) {
        Integer result = arithmeticService.tripleWaitWithFallback(number);
        HystrixInvokableInfo<?> lastCommand = getLastCommand();
		return new Response<Integer>(result, lastCommand.isResponseFromCache(), lastCommand.isResponseFromFallback());
    }
    
    private HystrixInvokableInfo<?> getLastCommand(){
        return Iterables.getLast(HystrixRequestLog.getCurrentRequest().getAllExecutedCommands());
    }

    // TODO: Implement a HystrixBadRequestException example
    // TODO: Implement request collapsing
}
