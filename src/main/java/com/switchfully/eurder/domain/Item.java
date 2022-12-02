package com.switchfully.eurder.domain;

import com.switchfully.eurder.exceptions.user.RequiredFieldIsEmptyException;

import java.util.Objects;
import java.util.UUID;

public class Item {
    private final String id;
    private String name;
    private String description;
    private double price;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price can't be negative");
        }
        this.price = price;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public void subtractAmount(long amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount can't be negative");
        }
        this.amount -= amount;
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
