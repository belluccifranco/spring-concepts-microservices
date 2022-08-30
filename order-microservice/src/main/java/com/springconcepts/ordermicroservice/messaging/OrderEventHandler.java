package com.springconcepts.ordermicroservice.messaging;

import com.springconcepts.sharedmodel.OrderEvent;
import com.springconcepts.sharedmodel.OrderState;
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
    private OrderEventListener orderEventListener;

    @Value("${CLOUDKARAFKA_USERNAME}-orders")
    private String ordersKafkaTopic;

    @Autowired
    public OrderEventHandler(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishOrderEvent(OrderEvent orderEvent) {
        log.info("Sending OrderEvent to " + ordersKafkaTopic);
        kafkaTemplate.send(ordersKafkaTopic, orderEvent);
    }

    //**************************************************************
    public void register(OrderEventListener orderEventListener) {
        this.orderEventListener = orderEventListener;
    }

    public void onEvent(OrderEvent orderEvent) {
        if (orderEventListener != null) {
            orderEventListener.onData(orderEvent);
        }
    }

    public void onComplete() {
        if (orderEventListener != null) {
            orderEventListener.processComplete();
        }
    }
    //**************************************************************

    @KafkaListener(
            groupId = "order-consumers",
            topics = "${kafka.topics.orders}",
            containerFactory = "orderKafkaListenerContainerFactory")
    public void consumeOrderEvent(OrderEvent orderEvent) {
        log.info("Listening topic: " + orderEvent);
        //if (orderEvent.getOrderState() == OrderState.ORDER_DONE) {
            onEvent(orderEvent);
        //}
    }
}
