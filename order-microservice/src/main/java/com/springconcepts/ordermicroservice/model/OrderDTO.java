package com.springconcepts.ordermicroservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

  @NotNull
  private String userId;

  @PositiveOrZero
  private Double amount;
}
