package com.switchfully.eurder.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Order {
    private final String id;
    private final String customerId;
    private final List<OrderLine> orderLines;

    public Order(String customerId, List<OrderLine> orderLines) {
        this.id = UUID.randomUUID().toString();
        this.customerId = customerId;
        this.orderLines = orderLines;
    }

    public String getId() {
        return id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }
}
