package com.springconcepts.berlinmicroservice.controller;

import com.springconcepts.berlinmicroservice.kafka.Producer;
import com.springconcepts.berlinmicroservice.kafka.SampleMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleController {

    private final Producer producer;

    @Autowired
    public ExampleController(Producer producer) {
        this.producer = producer;
    }

    @GetMapping("/hello")
    public String sayHelloWorld() {
        for (int i = 1; i < 20; i++) {
            producer.send(new SampleMessage(i, "A simple test message"));
        }
        return "Hello World!";
    }
}
