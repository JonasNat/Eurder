package com.switchfully.eurder.controllers;

import com.switchfully.eurder.domain.Role;
import com.switchfully.eurder.domain.User;
import com.switchfully.eurder.dto.CreateItemDTO;
import com.switchfully.eurder.dto.CreateOrderDTO;
import com.switchfully.eurder.dto.ItemDTO;
import com.switchfully.eurder.dto.OrderDTO;
import com.switchfully.eurder.security.SecurityService;
import com.switchfully.eurder.services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final SecurityService securityService;

    public OrderController(OrderService orderService, SecurityService securityService) {
        this.orderService = orderService;
        this.securityService = securityService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDTO placeOrder(@RequestBody CreateOrderDTO orderToPlace, @RequestHeader String authorization) {
        User customer = securityService.validateAuthorization(authorization, Role.CUSTOMER);
        return orderService.placeOrder(orderToPlace, customer.getId());
    }
}
