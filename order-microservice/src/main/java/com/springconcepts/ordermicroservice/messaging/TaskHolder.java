package com.springconcepts.ordermicroservice.messaging;

import com.springconcepts.sharedmodel.OrderEvent;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TaskHolder {

    private final Map<String, CompletableFuture<OrderEvent>> mapHolder = new ConcurrentHashMap<>();

    public void pushTask(String transactionId, CompletableFuture<OrderEvent> task) {
        mapHolder.put(transactionId, task);
    }

    public Optional<CompletableFuture<OrderEvent>> remove(String transactionId) {
        return Optional.ofNullable(mapHolder.remove(transactionId));
    }
}
