package com.switchfully.eurder.dto;

public class UpdateItemDTO {
    private String name;
    private String description;
    private double price;
    private long amount;

    public UpdateItemDTO(String name, String description, double price, long amount) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.amount = amount;
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
