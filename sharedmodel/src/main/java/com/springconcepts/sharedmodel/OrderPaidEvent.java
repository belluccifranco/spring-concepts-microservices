package com.springconcepts.sharedmodel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderPaidEvent {
    private Long orderId;
    private boolean isPaid;
}
