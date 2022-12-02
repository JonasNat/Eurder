package com.switchfully.eurder.dto;

import java.util.List;
import java.util.Objects;

public class OrderDTO {
    private final String customerId;
    private final List<OrderLineDTO> orderLines;
    private double totalPrice;

    public OrderDTO(String customerId, List<OrderLineDTO> orderLines) {
        this.customerId = customerId;
        this.orderLines = orderLines;
    }

    public String getCustomerId() {
        return customerId;
    }

    public List<OrderLineDTO> getOrderLines() {
        return orderLines;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (OrderDTO) obj;
        return Objects.equals(this.customerId, that.customerId) &&
                Objects.equals(this.orderLines, that.orderLines);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, orderLines);
    }

    @Override
    public String toString() {
        return "OrderDTO[" +
                "customerId=" + customerId + ", " +
                "orderLines=" + orderLines + ']';
    }


}
