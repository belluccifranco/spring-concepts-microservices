package com.springconcepts.ordermicroservice.messaging;

import com.springconcepts.sharedmodel.OrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderEventHandler {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${CLOUDKARAFKA_USERNAME}-orders")
    private String ordersKafkaTopic;

    @Autowired
    public OrderEventHandler(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishOrderEvent(OrderEvent orderEvent) {
        kafkaTemplate.send(ordersKafkaTopic, orderEvent);
        log.info("Sent OrderEvent to " + ordersKafkaTopic);
    }
}
