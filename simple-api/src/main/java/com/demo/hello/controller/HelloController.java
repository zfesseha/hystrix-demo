package com.demo.hello.controller;

import com.demo.hello.dto.Greeting;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

@RestController
@RequestMapping("/api/v1")
public class HelloController {

    private String[] names = new String[] {"Adam", "Bob", "Chelsea", "Diana", "Eve", "Frank", "Gina"};
    private String[] greetings = new String[] {"Hello", "Hi", "Hey", "What's up", "Greetings", "Good day"};
    private static final Random random = new Random();
    private final Map<Integer, Long> pastRequests = new HashMap<Integer, Long>();
    
    private static final Long MILLIS_IN_SEC = 1000l;
    private static final Long REQUEST_THRESHOLD_SEC = 5l;
    private static final Long REQUEST_THRESHOLD_MILLIS = REQUEST_THRESHOLD_SEC * MILLIS_IN_SEC;

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

    // TODO: Update the route, method name and corresponding client methods to throttled-triple
    @RequestMapping(method = RequestMethod.GET, value = "/triple-throttled/{number}")
    public Integer tripleOnce(@PathVariable("number") Integer number) {
    		Long lastRequest = pastRequests.get(number);
    		Long now = System.currentTimeMillis();
        if (lastRequest != null && (now - lastRequest < REQUEST_THRESHOLD_MILLIS)) {
            throw new IllegalArgumentException("A request to triple " + number + " was made "
            		+ ((double) (now - lastRequest)/MILLIS_IN_SEC) + " seconds ago.\nPlease wait " + REQUEST_THRESHOLD_SEC +
            		" seconds before making a request to triple the same number.");
        }
        pastRequests.put(number, now);
        return 3 * number;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/triple-wait/{number}")
    public Integer tripleWait(@PathVariable("number") Integer number) throws InterruptedException {
        int wait = random.nextInt(7000);
        System.out.println("wait:\t" + wait);
        Thread.sleep(wait);
        return 3 * number;
    }
}
