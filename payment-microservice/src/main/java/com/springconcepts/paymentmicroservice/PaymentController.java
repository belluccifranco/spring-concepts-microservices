package com.springconcepts.paymentmicroservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
public class PaymentController {

    @GetMapping("/flux")
    public Flux<String> getFlux() {
        return Flux.just("a", "b", "c")
                .delayElements(Duration.ofSeconds(1))
                .log();
    }
}
