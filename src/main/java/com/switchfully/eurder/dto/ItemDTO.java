package com.switchfully.eurder.dto;

public class ItemDTO {
    private final String name;
    private final String description;
    private final double Price;
    private final long amount;

    public ItemDTO(String name, String description, double price, long amount) {
        this.name = name;
        this.description = description;
        Price = price;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return Price;
    }

    public long getAmount() {
        return amount;
    }
}
