package com.nsf.hello.controller;

import com.nsf.hello.dto.Greeting;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@RestController
@RequestMapping("/api/v1")
public class HelloController {

    private String[] names = new String[] {"Adam", "Bob", "Chelsea", "Denise", "Eve", "Frank"};
    private String[] greetings = new String[] {"Hello", "Hi", "Hey", "What's up"};
    private static final Random random = new Random();
    private final Set<Integer> tripleRequests = new HashSet();

    @RequestMapping(method = RequestMethod.GET, value = "/greeting")
    public Greeting greeting() {
        int nameIndex = random.nextInt(names.length);
        int greetingIndex = random.nextInt(greetings.length);
        return new Greeting(greetings[greetingIndex], names[nameIndex]);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/triple/{number}")
    public Integer triple(@PathVariable("number") Integer number) {
        return 3 * number;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/triple-once/{number}")
    public Integer tripleOnce(@PathVariable("number") Integer number) {
        if (tripleRequests.contains(number)) {
            throw new IllegalArgumentException("A request has been made to triple " + number + " already.");
        }
        tripleRequests.add(number);
        return 3 * number;
    }
}
