package com.switchfully.eurder.mapper;

import com.switchfully.eurder.domain.Order;
import com.switchfully.eurder.domain.OrderLine;
import com.switchfully.eurder.dto.CreateOrderDTO;
import com.switchfully.eurder.dto.CreateOrderLineDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper {

    public Order toOrder(CreateOrderDTO orderToCreate, String customerId) {
        return new Order(customerId, this.toOrderLines(orderToCreate.orderLines()));
    }

    public OrderLine toOrderLine(CreateOrderLineDTO orderLineToCreate) {
        return new OrderLine(orderLineToCreate.itemId(), orderLineToCreate.amount());
    }

    public List<OrderLine> toOrderLines(List<CreateOrderLineDTO> orderLinesToCreate) {
        return orderLinesToCreate.stream().map(this::toOrderLine).toList();
    }

}
