package com.springconcepts.ordermicroservice.messaging;

import com.springconcepts.sharedmodel.OrderEvent;

public interface OrderEventListener {

    void onData(OrderEvent event);

    void processComplete();
}
