package com.springconcepts.ordermicroservice.service;

import com.springconcepts.ordermicroservice.model.Order;
import com.springconcepts.ordermicroservice.model.OrderDTO;
import com.springconcepts.sharedmodel.NewOrderEvent;
import com.springconcepts.ordermicroservice.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@Slf4j
public class OrderService {

  private final KafkaTemplate<String, Object> kafkaTemplate;
  private final OrderRepository orderRepository;

  @Value("${CLOUDKARAFKA_USERNAME}-new-order")
  private String newOrderKafkaTopic;

//  @Value("${CLOUDKARAFKA_USERNAME}-order-paid")
//  private String orderPaidKafkaTopic;

  public OrderService(KafkaTemplate<String, Object> kafkaTemplate,
                      OrderRepository orderRepository) {
    this.kafkaTemplate = kafkaTemplate;
    this.orderRepository = orderRepository;
  }

  public void createOrder(OrderDTO orderDTO) {
    Order savedOrder = orderRepository.save(convertToOrder(orderDTO));
    kafkaTemplate.send(newOrderKafkaTopic, createNewOrderEvent(savedOrder));
    log.info("Sent newOrderEvent to " + newOrderKafkaTopic);
  }

  private Order convertToOrder(OrderDTO orderDTO) {
    return Order.builder()
            .dateTime(LocalDateTime.now())
            .userId(orderDTO.getUserId())
            .amount(orderDTO.getAmount())
            .build();
  }

  private NewOrderEvent createNewOrderEvent(Order order) {
    return NewOrderEvent.builder()
            .orderId(order.getOrderId())
            .dateTime(order.getDateTime())
            .userId(order.getUserId())
            .amount(order.getAmount())
            .build();
  }
}
