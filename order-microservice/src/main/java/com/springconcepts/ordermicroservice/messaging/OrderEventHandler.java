package com.springconcepts.ordermicroservice.messaging;

import com.springconcepts.sharedmodel.OrderEvent;
import com.springconcepts.sharedmodel.OrderState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class OrderEventHandler {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final TaskHolder taskHolder;

    @Value("${CLOUDKARAFKA_USERNAME}-orders")
    private String ordersKafkaTopic;

    @Autowired
    public OrderEventHandler(KafkaTemplate<String, Object> kafkaTemplate,
                             TaskHolder taskHolder) {
        this.kafkaTemplate = kafkaTemplate;
        this.taskHolder = taskHolder;
    }

    public void publishOrderEvent(OrderEvent orderEvent) {
        log.info("Sending OrderEvent to " + ordersKafkaTopic);
        kafkaTemplate.send(ordersKafkaTopic, orderEvent);
    }

    @KafkaListener(
            groupId = "order-consumers",
            topics = "${kafka.topics.orders}",
            containerFactory = "orderKafkaListenerContainerFactory")
    public void consumeOrderEvent(OrderEvent orderEvent) {
        log.info("Listening topic: " + orderEvent);
        if (orderEvent.getOrderState() == OrderState.ORDER_DONE) {
            taskHolder.remove(orderEvent.getEventTransactionId())
                    .orElse(new CompletableFuture<>())
                    .complete(orderEvent);
        }
    }
}
