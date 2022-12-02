package com.switchfully.eurder.domain;

import java.time.LocalDate;
import java.util.UUID;

public class OrderLine {
    private final String id;
    private final String itemId;
    private final long amount;
    private LocalDate shippingDate = null;
    private double orderLinePrice;
    private String orderId;

    public OrderLine(String itemId, long amount) {
        id = UUID.randomUUID().toString();
        this.itemId = itemId;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public String getItemId() {
        return itemId;
    }

    public long getAmount() {
        return amount;
    }

    public LocalDate getShippingDate() {
        return shippingDate;
    }

    public void setShippingDate(LocalDate shippingDate) {
        this.shippingDate = shippingDate;
    }

    public double getOrderLinePrice() {
        return orderLinePrice;
    }

    public void setOrderLinePrice(double orderLinePrice) {
        this.orderLinePrice = orderLinePrice;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
