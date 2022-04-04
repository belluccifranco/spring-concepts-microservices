package com.springconcepts.usermicroservice.service;

import com.springconcepts.usermicroservice.model.User;
import com.springconcepts.usermicroservice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

  public boolean isValidUser(String userId) {
    if (userId != null) {
      User user = findUserByUserId(userId);
      return user != null && user.getActive() != null && user.getActive();
    }
    return false;
  }
}
