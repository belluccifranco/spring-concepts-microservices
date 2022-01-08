package com.springconcepts.usermicroservice.model.shared;

import lombok.AllArgsConstructor;
import lombok.Data;
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
