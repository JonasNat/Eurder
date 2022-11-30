package com.switchfully.eurder.dto;

public class OrderLineDTO {
    private final String itemName;
    private final long amount;
    private final double totalPrice;

    public OrderLineDTO(String itemName, long amount, double totalPrice) {
        this.itemName = itemName;
        this.amount = amount;
        this.totalPrice = totalPrice;
    }

    public String getItemName() {
        return itemName;
    }

    public long getAmount() {
        return amount;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}
