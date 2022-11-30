package com.switchfully.eurder.domain;

import java.time.LocalDate;
import java.util.UUID;

public class OrderLine {
    private final String id;
    private final String itemId;
    private final long amount;
    private final LocalDate shippingDate;

    public OrderLine(String itemId, long amount) {
        id = UUID.randomUUID().toString();
        this.itemId = itemId;
        this.amount = amount;
        shippingDate = LocalDate.now();
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
}
