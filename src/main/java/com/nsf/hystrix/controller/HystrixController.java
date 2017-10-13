package com.nsf.hystrix.controller;

import com.nsf.hello.client.HelloClient;
import com.nsf.hello.dto.Greeting;
import com.nsf.hystrix.command.HelloCommand;
import com.nsf.hystrix.command.TripleCommand;
import com.nsf.hystrix.command.TripleOnceCommand;

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
    public Greeting greeting() {
        HelloCommand command = new HelloCommand(helloClient);
        return command.execute();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/triple/{number}")
    public Integer triple(@PathVariable("number") Integer number) {
        TripleCommand command = new TripleCommand(helloClient, number);
        return command.execute();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/triple-all")
    public Integer[] tripleAll(@RequestParam("numbers") String numbersParam) {
    		// TODO: Validate query param before sending downstream
    		String [] numbersString = numbersParam.split(",");
    		
    		// TODO: start-functional - use functional pattern for this
    		Integer [] numbers = new Integer[numbersString.length];
    		int index = 0;
    		for (String numString : numbersString) {
    			TripleOnceCommand command = new TripleOnceCommand(helloClient, Integer.valueOf(numString));
    			numbers[index] = command.execute();
    			index++;
    		}
    		// TODO: end-functional
        return numbers;
    }

    // TODO: Implement a HystrixBadRequestException example
}
