package com.springconcepts.ordermicroservice.controller;

import com.springconcepts.ordermicroservice.messaging.OrderEventHandler;
import com.springconcepts.ordermicroservice.messaging.TaskHolder;
import com.springconcepts.ordermicroservice.model.OrderDTO;
import com.springconcepts.ordermicroservice.service.OrderService;
import com.springconcepts.sharedmodel.OrderEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import javax.validation.Valid;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
public class OrderController {

    public final OrderService orderService;
    public final OrderEventHandler orderEventHandler;
    private final TaskHolder taskHolder;

    @Autowired
    public OrderController(OrderService orderService, OrderEventHandler orderEventHandler,
                           TaskHolder taskHolder) {
        this.orderService = orderService;
        this.orderEventHandler = orderEventHandler;
        this.taskHolder = taskHolder;
    }

    @PostMapping(value = "/order", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<OrderEvent> createNewOrder(@Valid @RequestBody OrderDTO orderDTO) {
        String eventTransactionId = UUID.randomUUID().toString();
        orderDTO.setEventTransactionId(eventTransactionId);
        orderService.createOrder(orderDTO);
        var completableFuture = new CompletableFuture<OrderEvent>();
        taskHolder.pushTask(eventTransactionId, completableFuture);
        return Mono.fromFuture(completableFuture);
    }

}
