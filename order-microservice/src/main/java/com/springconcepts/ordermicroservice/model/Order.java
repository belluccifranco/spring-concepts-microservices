package com.springconcepts.ordermicroservice.model;

import com.springconcepts.sharedmodel.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long orderId;

  private LocalDateTime dateTime;

  private String userId;

  private Currency currency;

  private Double amount;
}
