package com.springconcepts.ordermicroservice.model.shared;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewOrderEvent {

  private Long orderId;
  private LocalDateTime dateTime;
  private Long userId;
  private Double amount;
}
