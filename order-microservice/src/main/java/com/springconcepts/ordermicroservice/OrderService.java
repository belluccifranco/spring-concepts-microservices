package com.springconcepts.ordermicroservice;

import com.springconcepts.ordermicroservice.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${CLOUDKARAFKA_USERNAME}-order-new")
    private String topic;

    public OrderService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void createOrder(OrderDTO orderDTO) {
        kafkaTemplate.send(topic, "Testing...");
        log.info("Sent sample message to " + topic);
    }

}
