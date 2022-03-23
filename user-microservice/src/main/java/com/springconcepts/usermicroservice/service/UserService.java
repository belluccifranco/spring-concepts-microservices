package com.springconcepts.usermicroservice.service;

import com.springconcepts.usermicroservice.exception.ServiceException;
import com.springconcepts.usermicroservice.model.User;
import com.springconcepts.usermicroservice.repository.UserRepository;
import com.springconcepts.sharedmodel.NewOrderEvent;
import com.springconcepts.sharedmodel.OrderPaidEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Slf4j
public class UserService {

  private final UserRepository userRepository;

  @Autowired
  public UserService(UserRepository userRepository) {
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
    //TODO check user
    if (findUserByUserId(newOrderEvent.getUserId()) == null) {
      //TODO push invalidUserEvent
      throw new ServiceException("User not valid!");
    }
    // Add kafka topic
    
    //TODO push orderCheckedEvent
    log.info("Listening topic: " + newOrderEvent.toString());
  }

  @KafkaListener(
      topics = "${kafka.topics.order-paid-event-topic}",
      containerFactory = "orderPaidKafkaListenerContainerFactory")
  public void pullOrderPaidEvent(OrderPaidEvent orderPaidEvent) {
    log.info("Listening topic: " + orderPaidEvent.toString());
  }
}
