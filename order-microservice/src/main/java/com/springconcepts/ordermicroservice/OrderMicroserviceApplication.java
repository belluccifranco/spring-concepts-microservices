package com.springconcepts.ordermicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class OrderMicroserviceApplication {

  public static void main(String[] args) {
    SpringApplication.run(OrderMicroserviceApplication.class, args);
  }

//  @Bean
//  public CommandLineRunner demo(OrderRepository repository) {
//    return args -> {
//      repository.save(new Order(1L, 456.5));
//      repository.save(new Order(2L, 556.5));
//      repository.save(new Order(3L, 656.5));
//    };
//  }
}
