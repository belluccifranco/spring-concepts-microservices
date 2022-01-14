package com.springconcepts.ordermicroservice.controller;

import com.springconcepts.ordermicroservice.model.OrderDTO;
import com.springconcepts.ordermicroservice.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class OrderController {

    public final OrderService orderService;

    @PostMapping("/")
    public void createNewOrder(@RequestBody OrderDTO orderDTO) {
        orderService.createOrder(orderDTO);
    }
}