package com.switchfully.eurder.domain;

import com.switchfully.eurder.exceptions.user.RequiredFieldIsEmptyException;

import java.util.Objects;
import java.util.UUID;

public class Item {
    private final String id;
    private final String name;
    private final String description;
    private final double price;
    private long amount;

    public Item(String name, String description, double price, long amount) {
        id = UUID.randomUUID().toString();
        this.name = validateField(name);
        this.description = description;
        this.price = price;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    private String validateField(String field) {
        if (field == null || field.isEmpty()) {
            throw new RequiredFieldIsEmptyException("Required field missing");
        }
        return field;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return name.equals(item.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
