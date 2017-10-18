package com.demo.hystrix.controller;

import com.demo.hystrix.command.HelloCommand;
import com.demo.hello.client.HelloClient;
import com.demo.hello.dto.Greeting;
import com.demo.hystrix.command.TripleCommand;
import com.demo.hystrix.command.TripleOnceCommand;

import com.demo.hystrix.rest.Response;
import com.demo.hystrix.service.GreetingService;

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

    @RequestMapping(method = RequestMethod.GET, value = "/greeting")
    public Response<Greeting> greeting() {
        HelloCommand command = new HelloCommand(helloClient);
        return new Response<Greeting>(command.execute(), command.isResponseFromCache(), command.isResponseFromFallback());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/anotation/greeting")
    public Response<Greeting> annotationGreeting() {
        return new Response<Greeting>(greetingService.greeting(), false, false);
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

    // TODO: Implement a HystrixBadRequestException example
}
