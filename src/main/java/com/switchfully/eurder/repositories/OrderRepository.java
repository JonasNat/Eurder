package com.switchfully.eurder.repositories;

import com.switchfully.eurder.domain.Order;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrderRepository {
    private final Map<String, Order> orders = new HashMap<>();

    public List<Order> getAll() {
        return orders.values().stream().toList();
    }

    public Order create(Order order) {
        orders.put(order.getId(), order);
        return order;
    }
}
