package com.nsf.hello.dto;

public class Greeting {

    private String message;
    private String name;

    public Greeting(String message, String name) {
        this.name = name;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getName() {
        return name;
    }
}
