package com.springconcepts.usermicroservice.service;

import com.springconcepts.usermicroservice.model.User;
import com.springconcepts.usermicroservice.model.shared.NewOrderEvent;
import com.springconcepts.usermicroservice.model.shared.OrderPaidEvent;
import com.springconcepts.usermicroservice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl {

  private final UserRepository userRepository;

  @Autowired
  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public List<User> findUsers() {
    return userRepository.findAll();
  }

  public User saveUser(User user) {
    return userRepository.save(user);
  }

  public User findUserByUserId(String userId) {
    return userRepository.findById(userId).orElse(null);
  }

  @KafkaListener(
      topics = "${kafka.topics.new-order-event-topic}",
      containerFactory = "newOrderKafkaListenerContainerFactory")
  public void pullNewOrderEvent(NewOrderEvent newOrderEvent) {
    log.info("Listening topic: " + newOrderEvent.toString());
    log.info(newOrderEvent.getClass().toString());
  }

  @KafkaListener(
      topics = "${kafka.topics.order-paid-event-topic}",
      containerFactory = "orderPaidKafkaListenerContainerFactory")
  public void pullOrderPaidEvent(OrderPaidEvent orderPaidEvent) {
    log.info("Listening topic: " + orderPaidEvent.toString());
    log.info(orderPaidEvent.getClass().toString());
  }
}
