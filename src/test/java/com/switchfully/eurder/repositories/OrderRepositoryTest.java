package com.switchfully.eurder.repositories;

import com.switchfully.eurder.domain.Order;
import com.switchfully.eurder.domain.OrderLine;
import com.switchfully.eurder.exceptions.order.OrderNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;

class OrderRepositoryTest {
    private OrderLine line1;
    private OrderLine line2;
    private OrderLine line3;
    private Order order1;
    private Order order2;
    private final OrderRepository orderRepository = new OrderRepository();

    @BeforeEach
    void setup() {
        line1 = new OrderLine("1", 5);
        line2 = new OrderLine("2", 10);
        line3 = new OrderLine("3", 20);
        order1 = new Order("1", List.of(line1, line2));
        order2 = new Order("2", List.of(line3));
    }

    @Test
    void givenARepositoryOfOrders_whenCreatingANewOrder_orderIsAddedToTheRepository() {
        orderRepository.create(order1);

        assertThat(orderRepository.getAll()).contains(order1);
    }

    @Test
    void givenARepositoryOfOrders_whenSearchingById_returnsCorrectOrder() {
        orderRepository.create(order1);

        assertThat(orderRepository.findById(order1.getId())).hasValueSatisfying(order -> assertThat(order.getOrderLines()).containsExactlyInAnyOrder(line1, line2));
    }

    @Test
    void givenARepositoryOfOrders_whenGettingAllOrders_returnsAllOrders() {
        orderRepository.create(order1);
        orderRepository.create(order2);

        assertThat(orderRepository.getAll()).containsExactlyInAnyOrder(order1, order2);
    }
}