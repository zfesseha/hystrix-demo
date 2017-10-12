package com.nsf.hello.controller;

import com.nsf.hello.dto.Greeting;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("/api/v1/hello")
public class HelloController {

    private String[] names = new String[] {"Adam", "Bob", "Chelsea", "Denise", "Eve", "Frank"};
    private String[] greetings = new String[] {"Hello", "Hi", "Hey", "What's up"};
    private static final Random random = new Random();

    @RequestMapping(method = RequestMethod.GET)
    public Greeting greeting() {
        int nameIndex = random.nextInt(names.length);
        int greetingIndex = random.nextInt(greetings.length);
        return new Greeting(greetings[greetingIndex], names[nameIndex]);
    }
}
