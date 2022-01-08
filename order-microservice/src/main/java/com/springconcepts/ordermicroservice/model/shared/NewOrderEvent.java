package com.springconcepts.ordermicroservice.model.shared;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewOrderEvent implements Serializable {

  private Long orderId;
  private LocalDateTime dateTime;
  private Long userId;
  private Double amount;
}
