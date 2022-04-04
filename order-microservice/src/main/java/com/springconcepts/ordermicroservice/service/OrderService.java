package com.springconcepts.ordermicroservice.service;

import com.springconcepts.ordermicroservice.exception.BusinessException;
import com.springconcepts.ordermicroservice.messaging.OrderEventHandler;
import com.springconcepts.ordermicroservice.model.Order;
import com.springconcepts.ordermicroservice.model.OrderDTO;
import com.springconcepts.sharedmodel.Currency;
import com.springconcepts.sharedmodel.OrderEvent;
import com.springconcepts.ordermicroservice.repository.OrderRepository;
import com.springconcepts.sharedmodel.OrderState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@Slf4j
public class OrderService {

  private final OrderRepository orderRepository;
  private final OrderEventHandler orderEventHandler;
  private static final String INVALID_CONVERSION_PAIR_MESSAGE = "Invalid conversion pair";

  @Autowired
  public OrderService(OrderRepository orderRepository, OrderEventHandler orderEventHandler) {
    this.orderRepository = orderRepository;
    this.orderEventHandler = orderEventHandler;
  }

  public void createOrder(OrderDTO orderDTO) {
    Order savedOrder = orderRepository.save(mapToOrder(orderDTO));
    orderEventHandler.publishOrderEvent(createNewOrderEvent(savedOrder));
  }

  private Order mapToOrder(OrderDTO orderDTO) {
    return Order.builder()
            .dateTime(LocalDateTime.now())
            .userId(orderDTO.getUserId())
            .currency(orderDTO.getPairTo())
            .amount(applyConversion(orderDTO.getPairFrom(), orderDTO.getPairTo(), orderDTO.getAmount()))
            .build();
  }

  private Double applyConversion(Currency from, Currency to, Double amount) {
    return switch (from) {
      case EUR -> switch (to) {
        case USD -> amount * 1.1;
        case BTC -> amount * 1.5;
        default -> throw new BusinessException(INVALID_CONVERSION_PAIR_MESSAGE);
      };

      case USD -> switch (to) {
        case EUR -> amount * 1.1;
        case BTC -> amount * 1.6;
        default -> throw new BusinessException(INVALID_CONVERSION_PAIR_MESSAGE);
      };

      case BTC -> switch (to) {
        case EUR -> amount * 1.5;
        case USD -> amount * 1.6;
        default -> throw new BusinessException(INVALID_CONVERSION_PAIR_MESSAGE);
      };
    };
  }

  private OrderEvent createNewOrderEvent(Order order) {
    return OrderEvent.builder()
            .orderId(order.getOrderId())
            .dateTime(order.getDateTime())
            .orderState(OrderState.ORDER_NEW)
            .userId(order.getUserId())
            .currency(order.getCurrency())
            .amount(order.getAmount())
            .build();
  }
}
