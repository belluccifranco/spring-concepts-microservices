package com.springconcepts.ordermicroservice.controller;

import com.springconcepts.ordermicroservice.messaging.OrderEventHandler;
import com.springconcepts.ordermicroservice.messaging.OrderEventListener;
import com.springconcepts.ordermicroservice.model.OrderDTO;
import com.springconcepts.ordermicroservice.service.OrderService;
import com.springconcepts.sharedmodel.OrderEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import javax.validation.Valid;

@RestController
public class OrderController {

    public final OrderService orderService;
    public final OrderEventHandler orderEventHandler;
    private final Flux<OrderEvent> bridge;

    @Autowired
    public OrderController(OrderService orderService, OrderEventHandler orderEventHandler) {
        this.orderService = orderService;
        this.orderEventHandler = orderEventHandler;
        this.bridge = createBridge().publish().autoConnect().cache(10).log();
    }

    private Flux<OrderEvent> createBridge() {
        return Flux.create(sink -> {
            orderEventHandler.register(new OrderEventListener() {

                @Override
                public void processComplete() {
                    sink.complete();
                }

                @Override
                public void onData(OrderEvent data) {
                    sink.next(data);
                }
            });
        });
    }


    @PostMapping(value = "/order", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<OrderEvent> createNewOrder(@Valid @RequestBody OrderDTO orderDTO) {
        orderService.createOrder(orderDTO);
        return bridge;
    }

}
