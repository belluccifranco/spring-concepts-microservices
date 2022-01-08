package com.springconcepts.paymentmicroservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
@Slf4j
public class PaymentController {

    @GetMapping(value = "/flux", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Payment> getFlux() {
        return Flux.range(1, 50)
                .delayElements(Duration.ofSeconds(1))
                .doOnNext(i -> log.info("Processing " + i))
                .map(Payment::new);
    }

    public record Payment(Integer id) {}
}
