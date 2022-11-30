package com.switchfully.eurder.controllers;

import com.switchfully.eurder.domain.Address;
import com.switchfully.eurder.domain.Role;
import com.switchfully.eurder.domain.User;
import com.switchfully.eurder.dto.CreateItemDTO;
import com.switchfully.eurder.dto.ItemDTO;
import com.switchfully.eurder.repositories.UserRepository;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ItemControllerTest {
    @Autowired
    private UserRepository userRepository;
    private User admin;
    private User customer;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        admin = new User(
                "Jonas",
                "Nata",
                "jonas@eurder.com",
                "password",
                new Address("Teststraat", "50", "2000", "Antwerp"),
                "0498416686",
                Role.ADMIN
        );

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
    }

    @Test
    void givenARequestBody_whenAddingANewItem_httpStatusCreatedAndNewItemReturned() {
        CreateItemDTO itemToAdd = new CreateItemDTO(
                "name",
                "testItem",
                6.0,
                30
        );

        ItemDTO itemDTO =
                RestAssured
                        .given().contentType(JSON).body(itemToAdd).accept(JSON)
                        .auth().preemptive().basic(admin.getEmailAddress(), admin.getPassword())
                        .when().port(port).post("/items")
                        .then().assertThat().statusCode(HttpStatus.SC_CREATED).extract().as(ItemDTO.class);

        assertThat(itemDTO.getName()).isEqualTo(itemToAdd.name());
        assertThat(itemDTO.getDescription()).isEqualTo(itemToAdd.description());
        assertThat(itemDTO.getPrice()).isEqualTo(itemToAdd.price());
        assertThat(itemDTO.getAmount()).isEqualTo(itemToAdd.amount());
    }

    @Test
    void givenARequestBodyWithMissingName_whenAddingNewItem_httpStatusBadRequest() {
        CreateItemDTO itemToAdd = new CreateItemDTO(
                "",
                "testItem",
                6.0,
                30
        );

        RestAssured
                .given().contentType(JSON).body(itemToAdd).accept(JSON)
                .auth().preemptive().basic(admin.getEmailAddress(), admin.getPassword())
                .when().port(port).post("/users")
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    void givenRequestHeaderWithIncorrectAuthorization_whenAddingItem_httpStatusForbidden() {
        CreateItemDTO itemToAdd = new CreateItemDTO(
                "name",
                "testItem",
                6.0,
                30
        );

        RestAssured
                .given().contentType(JSON).body(itemToAdd).accept(JSON)
                .auth().preemptive().basic(admin.getEmailAddress(), "invalidPassword")
                .when().port(port).post("/items")
                .then().assertThat().statusCode(HttpStatus.SC_FORBIDDEN);
    }

    @Test
    void givenCustomer_whenAddingItem_httpStatusForbidden() {
        CreateItemDTO itemToAdd = new CreateItemDTO(
                "name",
                "testItem",
                6.0,
                30
        );

        RestAssured
                .given().contentType(JSON).body(itemToAdd).accept(JSON)
                .auth().preemptive().basic(customer.getEmailAddress(), customer.getPassword())
                .when().port(port).post("/items")
                .then().assertThat().statusCode(HttpStatus.SC_FORBIDDEN);
    }

    @Test
    void givenUnknownUser_whenAddingItem_httpStatusForbidden() {
        CreateItemDTO itemToAdd = new CreateItemDTO(
                "name",
                "testItem",
                6.0,
                30
        );

        RestAssured
                .given().contentType(JSON).body(itemToAdd).accept(JSON)
                .auth().preemptive().basic("invalidEmail", customer.getPassword())
                .when().port(port).post("/items")
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST);
    }
}