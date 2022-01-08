package com.springconcepts.ordermicroservice.repository;

import com.springconcepts.ordermicroservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {}
