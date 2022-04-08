package com.springconcepts.usermicroservice.messaging;

import com.springconcepts.sharedmodel.OrderEvent;
import com.springconcepts.sharedmodel.OrderState;
import com.springconcepts.usermicroservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderEventHandler {

  private final KafkaTemplate<String, Object> kafkaTemplate;
  private final UserService userService;

  @Value("${CLOUDKARAFKA_USERNAME}-orders")
  private String ordersKafkaTopic;

  @Autowired
  public OrderEventHandler(KafkaTemplate<String, Object> kafkaTemplate, UserService userService) {
    this.kafkaTemplate = kafkaTemplate;
    this.userService = userService;
  }

  @KafkaListener(
      groupId = "user-consumers",
      topics = "${kafka.topics.orders}",
      containerFactory = "newOrderKafkaListenerContainerFactory")
  public void consumeOrderEvent(OrderEvent orderEvent) {
    log.info("Listening topic: " + orderEvent);
    if (orderEvent.getOrderState() == OrderState.ORDER_NEW) {
      if (userService.isValidUser(orderEvent.getUserId())) {
        orderEvent.setOrderState(OrderState.ORDER_CHECKED);
      } else {
        orderEvent.setOrderState(OrderState.ORDER_REJECTED);
      }
      publishOrderEvent(orderEvent);
    }
  }

  public void publishOrderEvent(OrderEvent orderEvent) {
    log.info("Sending OrderEvent to " + ordersKafkaTopic);
    kafkaTemplate.send(ordersKafkaTopic, orderEvent);
  }
}
