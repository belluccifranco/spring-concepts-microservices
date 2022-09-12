package com.springconcepts.accountmicroservice.messaging;

import com.springconcepts.sharedmodel.OrderEvent;
import com.springconcepts.sharedmodel.OrderState;
import com.springconcepts.accountmicroservice.service.AccountService;
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
    private final AccountService accountService;

    @Value("${CLOUDKARAFKA_USERNAME}-orders")
    private String ordersKafkaTopic;

    @Autowired
    public OrderEventHandler(KafkaTemplate<String, Object> kafkaTemplate,
                             AccountService accountService) {
        this.kafkaTemplate = kafkaTemplate;
        this.accountService = accountService;
    }

    @KafkaListener(
            groupId = "account-consumers",
            topics = "${kafka.topics.orders}",
            containerFactory = "orderKafkaListenerContainerFactory")
    public void consumeOrderEvent(OrderEvent orderEvent) {
        log.info("Listening topic: " + orderEvent);
        if (orderEvent.getOrderState() == OrderState.ORDER_CHECKED) {
            accountService.updateBalance(orderEvent.getUserId(), orderEvent.getAmount());
            orderEvent.setOrderState(OrderState.ORDER_DONE);
            publishOrderEvent(orderEvent);
        }
    }

    public void publishOrderEvent(OrderEvent orderEvent) {
        log.info("Sending OrderEvent to " + ordersKafkaTopic);
        kafkaTemplate.send(ordersKafkaTopic, orderEvent);
    }
}
