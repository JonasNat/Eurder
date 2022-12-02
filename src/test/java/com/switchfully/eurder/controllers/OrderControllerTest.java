package com.switchfully.eurder.controllers;

import com.switchfully.eurder.domain.Address;
import com.switchfully.eurder.domain.Item;
import com.switchfully.eurder.domain.Role;
import com.switchfully.eurder.domain.User;
import com.switchfully.eurder.dto.*;
import com.switchfully.eurder.mapper.OrderMapper;
import com.switchfully.eurder.repositories.ItemRepository;
import com.switchfully.eurder.repositories.OrderRepository;
import com.switchfully.eurder.repositories.UserRepository;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import static io.restassured.http.ContentType.JSON;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class OrderControllerTest {

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderMapper orderMapper;
    private Item item1;
    private Item item2;
    private User customer;
    private CreateOrderDTO orderToPlace;
    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        item1 = new Item("Laptop", "Laptop", 400, 5);
        item2 = new Item("Book", "Book", 15, 20);
        itemRepository.create(item1);
        itemRepository.create(item2);
        CreateOrderLineDTO orderLine1 = new CreateOrderLineDTO(item1.getId(), 1);
        CreateOrderLineDTO orderLine2 = new CreateOrderLineDTO(item2.getId(), 5);
        orderToPlace = new CreateOrderDTO(List.of(orderLine1, orderLine2));

        customer = new User(
                "Frederik",
                "Sok",
                "frederik@eurder.com",
                "password",
                new Address("Teststraat", "50", "2000", "Antwerp"),
                "0498416686",
                Role.CUSTOMER
        );
        userRepository.create(customer);
        orderRepository = new OrderRepository();
    }

    @Test
    void givenARequestBody_whenPlacingANewOrder_httpStatusCreatedAndNewOrderReturned() {
        OrderDTO orderDTO2 =
                RestAssured
                        .given().contentType(JSON).body(orderToPlace).accept(JSON)
                        .auth().preemptive().basic(customer.getEmailAddress(), customer.getPassword())
                        .when().port(port).post("/orders")
                        .then().assertThat().statusCode(HttpStatus.SC_CREATED).extract().as(OrderDTO.class);

        assertThat(orderDTO2.getCustomerId()).isEqualTo(customer.getId());
        assertThat(orderDTO2.getOrderLines().stream().mapToDouble(OrderLineDTO::amount).sum())
                .isEqualTo(orderToPlace.orderLines().stream().mapToDouble(CreateOrderLineDTO::amount).sum());
    }

    @Test
    void givenARequestBodyWithInvalidItemId_whenPlacingANewOrder_httpStatusBadRequest() {
        CreateOrderLineDTO orderLine3 = new CreateOrderLineDTO("InvalidId", 5);
        orderToPlace = new CreateOrderDTO(List.of(orderLine3));
                RestAssured
                        .given().contentType(JSON).body(orderToPlace).accept(JSON)
                        .auth().preemptive().basic(customer.getEmailAddress(), customer.getPassword())
                        .when().port(port).post("/orders")
                        .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    void givenRequestHeaderWithIncorrectAuthorization_whenAddingItem_httpStatusForbidden() {
        RestAssured
                .given().contentType(JSON).body(orderToPlace).accept(JSON)
                .auth().preemptive().basic(customer.getEmailAddress(), "invalidPassword")
                .when().port(port).post("/orders")
                .then().assertThat().statusCode(HttpStatus.SC_FORBIDDEN);
    }

    @Test
    void givenUnknownUser_whenAddingItem_httpStatusForbidden() {
        RestAssured
                .given().contentType(JSON).body(orderToPlace).accept(JSON)
                .auth().preemptive().basic("invalidEmail", customer.getPassword())
                .when().port(port).post("/orders")
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST);
    }
}