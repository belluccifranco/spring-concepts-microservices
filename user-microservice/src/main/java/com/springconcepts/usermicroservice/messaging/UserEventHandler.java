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
public class UserEventHandler {

  private final KafkaTemplate<String, Object> kafkaTemplate;
  private final UserService userService;

  @Value("${CLOUDKARAFKA_USERNAME}-orders")
  private String ordersKafkaTopic;

  @Autowired
  public UserEventHandler(KafkaTemplate<String, Object> kafkaTemplate, UserService userService) {
    this.kafkaTemplate = kafkaTemplate;
    this.userService = userService;
  }

  @KafkaListener(
      groupId = "user-consumers",
      topics = "${kafka.topics.orders}",
      containerFactory = "newOrderKafkaListenerContainerFactory")
  public void consumeOrderEvent(OrderEvent orderEvent) {
    if (orderEvent.getOrderState() == OrderState.ORDER_NEW) {
      log.info("Listening topic: " + orderEvent);
      if (userService.isValidUser(orderEvent.getUserId())) {
        orderEvent.setOrderState(OrderState.ORDER_CHECKED);
      } else {
        orderEvent.setOrderState(OrderState.ORDER_REJECTED);
      }
      publishOrderEvent(orderEvent);
    }
  }

  public void publishOrderEvent(OrderEvent orderEvent) {
    kafkaTemplate.send(ordersKafkaTopic, orderEvent);
    log.info("Sent OrderEvent to " + ordersKafkaTopic);
  }
}
