package com.springconcepts.ordermicroservice.model.shared;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPaidEvent {
    private Long orderId;
    private boolean isPaid;
}
