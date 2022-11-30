package com.switchfully.eurder.dto;

import java.time.LocalDate;
import java.util.Objects;

public record OrderLineDTO(String itemName, long amount, double totalPrice, LocalDate shippingDate) {

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (OrderLineDTO) obj;
        return Objects.equals(this.itemName, that.itemName) &&
                this.amount == that.amount &&
                Double.doubleToLongBits(this.totalPrice) == Double.doubleToLongBits(that.totalPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemName, amount, totalPrice);
    }

    @Override
    public String toString() {
        return "OrderLineDTO[" +
                "itemName=" + itemName + ", " +
                "amount=" + amount + ", " +
                "totalPrice=" + totalPrice + ']';
    }

}
