package com.springconcepts.ordermicroservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import com.springconcepts.sharedmodel.Currency;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

  @NotNull
  private String userId;

  @NotNull
  private Currency pairFrom;

  @NotNull
  private Currency pairTo;

  @PositiveOrZero
  private BigDecimal amount;
}
