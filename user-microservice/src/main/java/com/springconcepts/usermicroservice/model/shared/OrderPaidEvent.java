package com.springconcepts.usermicroservice.model.shared;

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
