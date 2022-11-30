package com.switchfully.eurder.dto;

import com.switchfully.eurder.domain.OrderLine;

import java.util.List;

public record OrderDTO(String customerId, List<OrderLineDTO> orderLines) {
    //    private final Double totalPrice;

}
