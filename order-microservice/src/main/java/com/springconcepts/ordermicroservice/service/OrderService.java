package com.springconcepts.ordermicroservice.service;

import com.springconcepts.ordermicroservice.model.OrderDTO;
import com.springconcepts.ordermicroservice.model.shared.NewOrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@Slf4j
public class OrderService {

  private final KafkaTemplate<String, Object> kafkaTemplate;

  @Value("${CLOUDKARAFKA_USERNAME}-new-order")
  private String kafkaTopic;

  public OrderService(KafkaTemplate<String, Object> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void createOrder(OrderDTO orderDTO) {
    // convert to Order

    // store in DB

    // push to the event bus

    var newOrderEvent =
        new NewOrderEvent(orderDTO.getOrderId(), LocalDateTime.now(), orderDTO.getUserId(), 5.0);
    kafkaTemplate.send(kafkaTopic, newOrderEvent);
    log.info("Sent sample message to " + kafkaTopic);
  }
}
