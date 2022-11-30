package com.switchfully.eurder.dto;

public class ItemDTO {
    private final String id;
    private final String name;
    private final String description;
    private final double price;
    private final long amount;

    public ItemDTO(String id, String name, String description, double price, long amount) {
        this.id = id;
        this.name = name;
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
}
