package com.nsf.hystrix.controller;

import com.nsf.hello.client.HelloClient;
import com.nsf.hello.dto.Greeting;
import com.nsf.hystrix.command.HelloCommand;
import com.nsf.hystrix.command.TripleCommand;
import com.nsf.hystrix.command.TripleOnceCommand;

import com.nsf.hystrix.rest.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @RequestMapping(method = RequestMethod.GET, value = "/greeting")
    public Response<Greeting> greeting() {
        HelloCommand command = new HelloCommand(helloClient);
        return new Response<Greeting>(command.execute(), command.isResponseFromCache(), command.isResponseFromFallback());
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
