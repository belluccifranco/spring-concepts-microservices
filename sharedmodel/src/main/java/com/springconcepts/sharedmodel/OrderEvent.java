package com.springconcepts.sharedmodel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderEvent implements Serializable {
    private String eventTransactionId;
    private Long orderId;
    private LocalDateTime dateTime;
    private OrderState orderState;
    private String userId;
    private Currency currency;
    private BigDecimal amount;
}
