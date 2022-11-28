package com.switchfully.eurder.domain;

import java.math.BigDecimal;
import java.util.UUID;

public class Item {
    private final String id;
    private final String name;
    private final BigDecimal price;
    private int amount;

    public Item(String name, BigDecimal price, int amount) {
        id = UUID.randomUUID().toString();
        this.name = name;
        this.price = price;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getAmount() {
        return amount;
    }
}
