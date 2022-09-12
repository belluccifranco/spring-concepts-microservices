package com.springconcepts.accountmicroservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long accountId;

  private String userId;

  @PositiveOrZero
  private BigDecimal balance;
}
