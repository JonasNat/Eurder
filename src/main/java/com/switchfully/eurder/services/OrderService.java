package com.switchfully.eurder.services;

import com.switchfully.eurder.dto.CreateOrderDTO;
import com.switchfully.eurder.dto.OrderDTO;
import com.switchfully.eurder.dto.OrderLineDTO;
import com.switchfully.eurder.exceptions.item.ItemNotFoundException;
import com.switchfully.eurder.mapper.OrderMapper;
import com.switchfully.eurder.repositories.ItemRepository;
import com.switchfully.eurder.repositories.OrderRepository;
import org.springframework.stereotype.Service;

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

        List<OrderLineDTO> orderLines = orderToPlace.orderLines().stream()
                .map(orderLine -> new OrderLineDTO(
                        itemRepository.findById(orderLine.itemId()).get().getName(),
                        orderLine.amount(),
                        orderLine.amount() * itemRepository.findById(orderLine.itemId()).get().getPrice())
                )
                .toList();

        orderRepository.create(orderMapper.toOrder(orderToPlace, customerId));
        return new OrderDTO(customerId, orderLines);
    }

}
