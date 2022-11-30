package com.switchfully.eurder.services;


import com.switchfully.eurder.domain.Item;
import com.switchfully.eurder.domain.Order;
import com.switchfully.eurder.domain.OrderLine;
import com.switchfully.eurder.dto.CreateOrderDTO;
import com.switchfully.eurder.dto.CreateOrderLineDTO;
import com.switchfully.eurder.exceptions.item.ItemNotFoundException;
import com.switchfully.eurder.mapper.OrderMapper;
import com.switchfully.eurder.repositories.ItemRepository;
import com.switchfully.eurder.repositories.OrderRepository;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.time.LocalDate;
import java.util.List;

class OrderServiceTest {
    private Item item1;
    private Item item2;
    private final OrderRepository orderRepository = new OrderRepository();
    private final ItemRepository itemRepository = new ItemRepository();
    private final OrderMapper orderMapper = new OrderMapper();
    private final OrderService orderService = new OrderService(orderRepository, itemRepository, orderMapper);

    @BeforeEach
    void setup() {
        item1 = new Item("Laptop", "Laptop", 400, 5);
        item2 = new Item("Book", "Book", 15, 20);
        itemRepository.create(item1);
        itemRepository.create(item2);
    }

    @Test
    void givenAnOrder_whenOrderingAnUnknownItem_throwException() {
        OrderLine invalidLine = new OrderLine("invalidId", 10);
        CreateOrderLineDTO orderLine1 = new CreateOrderLineDTO(item1.getId(), 1);
        CreateOrderLineDTO invalidOrderLine = new CreateOrderLineDTO("invalidId", 10);
        CreateOrderDTO order3 = new CreateOrderDTO(List.of(orderLine1, invalidOrderLine));

        assertThatExceptionOfType(ItemNotFoundException.class).isThrownBy(() ->  orderService.placeOrder(order3, "1"));
    }

    @Test
    void givenAnOrder_whenItemsAreInStock_shippingDateIsLocalDateNowPlus1Day() {
        CreateOrderLineDTO orderLine1 = new CreateOrderLineDTO(item1.getId(), 1);
        CreateOrderLineDTO orderLine2 = new CreateOrderLineDTO(item2.getId(), 5);
        CreateOrderDTO order3 = new CreateOrderDTO(List.of(orderLine1, orderLine2));

        orderService.placeOrder(order3, "1");
        List<Order> orders = orderRepository.findByCustomerId("1");

        assertThat(orders.stream().findFirst().get().getOrderLines()
                .stream()
                .map(OrderLine::getShippingDate)
                .allMatch(date -> date.compareTo(LocalDate.now().plusDays(1)) == 0))
                .isTrue();
    }

    @Test
    void givenAnOrder_whenItemsAreNotInStock_shippingDateIsLocalDateNowPlus7Day() {
        CreateOrderLineDTO orderLine1 = new CreateOrderLineDTO(item1.getId(), 6);
        CreateOrderDTO order3 = new CreateOrderDTO(List.of(orderLine1));

        orderService.placeOrder(order3, "1");
        List<Order> orders = orderRepository.findByCustomerId("1");

        assertThat(orders.stream().findFirst().get().getOrderLines().stream()
                .map(OrderLine::getShippingDate)
                .allMatch(date -> date.compareTo(LocalDate.now().plusDays(7)) == 0))
                .isTrue();
    }

    @Test
    void givenARepositoryOfItems_whenOrderingItem_itemAmountIsAdjustedAccordingly() {
        CreateOrderLineDTO orderLine1 = new CreateOrderLineDTO(item1.getId(), 3);
        CreateOrderDTO order3 = new CreateOrderDTO(List.of(orderLine1));

        orderService.placeOrder(order3, "1");

        assertThat(itemRepository.findById(item1.getId()).get().getAmount()).isEqualTo(5 - orderLine1.amount());
    }

    @Test
    void givenAnOrder_orderPriceIsCorrectlyCalculated() {
        CreateOrderLineDTO orderLine1 = new CreateOrderLineDTO(item1.getId(), 1);
        CreateOrderLineDTO orderLine2 = new CreateOrderLineDTO(item2.getId(), 5);
        CreateOrderDTO order3 = new CreateOrderDTO(List.of(orderLine1, orderLine2));

        orderService.placeOrder(order3, "1");
        List<Order> orders = orderRepository.findByCustomerId("1");

        assertThat(orders.stream().findFirst().get().getOrderPrice()).isEqualTo(475);
    }
}