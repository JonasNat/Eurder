package com.switchfully.eurder.services;

import com.switchfully.eurder.domain.Item;
import com.switchfully.eurder.domain.Order;
import com.switchfully.eurder.domain.OrderLine;
import com.switchfully.eurder.dto.CreateOrderDTO;
import com.switchfully.eurder.dto.CreateOrderLineDTO;
import com.switchfully.eurder.dto.OrderDTO;
import com.switchfully.eurder.dto.OrderLineDTO;
import com.switchfully.eurder.exceptions.item.ItemNotFoundException;
import com.switchfully.eurder.mapper.OrderMapper;
import com.switchfully.eurder.repositories.ItemRepository;
import com.switchfully.eurder.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final OrderMapper orderMapper;

    public OrderService(OrderRepository orderRepository, ItemRepository itemRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
        this.orderMapper = orderMapper;
    }

    public OrderDTO placeOrder(CreateOrderDTO orderToPlace, String customerId) {
        orderToPlace.orderLines().forEach(orderLine -> {
            if (itemRepository.findById(orderLine.itemId()).isEmpty()) {
                throw new ItemNotFoundException("Item not found");
            }
        });

        Order order = orderRepository.create(orderMapper.toOrder(orderToPlace, customerId));
        order.getOrderLines().forEach(orderLine -> orderLine.setShippingDate(calculateShippingDate(orderLine)));
        order.getOrderLines().forEach(orderLine -> itemRepository.findById(orderLine.getItemId()).get().subtractAmount(orderLine.getAmount()));
        order.getOrderLines().forEach(orderLine -> orderLine.setOrderLinePrice(orderLine.getAmount() * itemRepository.findById(orderLine.getItemId()).get().getPrice()));
        double orderPrice = order.setOrderPrice(calculateOrderPrice(order));

        List<OrderLineDTO> orderLines = order.getOrderLines().stream()
                .map(orderLine -> new OrderLineDTO(
                        itemRepository.findById(orderLine.getItemId()).get().getName(),
                        orderLine.getAmount(),
                        orderLine.getOrderLinePrice(),
                        orderLine.getShippingDate())
                )
                .toList();

        OrderDTO orderDTO = new OrderDTO(customerId, orderLines);
        orderDTO.setTotalPrice(orderPrice);
        return orderDTO;
    }

    public LocalDate calculateShippingDate(OrderLine orderLine) {
        Item item = itemRepository.findById(orderLine.getItemId()).orElseThrow(() -> new ItemNotFoundException("Item Not Found"));
        if (item.getAmount() < orderLine.getAmount()) {
            return LocalDate.now().plusDays(7);
        }
        return LocalDate.now().plusDays(1);
    }

    public double calculateOrderPrice(Order order) {
        return order.getOrderLines().stream().mapToDouble(OrderLine::getOrderLinePrice).sum();
    }



}
