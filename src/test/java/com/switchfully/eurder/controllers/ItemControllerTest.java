package com.switchfully.eurder.controllers;

import com.switchfully.eurder.domain.Address;
import com.switchfully.eurder.domain.Item;
import com.switchfully.eurder.domain.Role;
import com.switchfully.eurder.domain.User;
import com.switchfully.eurder.dto.CreateItemDTO;
import com.switchfully.eurder.dto.ItemDTO;
import com.switchfully.eurder.dto.UpdateItemDTO;
import com.switchfully.eurder.repositories.ItemRepository;
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
    private final UserRepository userRepository = new UserRepository();
    @Autowired
    private final ItemRepository itemRepository = new ItemRepository();
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
        userRepository.create(admin);
    }

    @Test
    void givenARequestBody_whenAddingANewItem_httpStatusCreatedAndNewItemReturned() {
        CreateItemDTO itemToAdd = new CreateItemDTO(
                "randomName",
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

        assertThat(itemDTO.name()).isEqualTo(itemToAdd.name());
        assertThat(itemDTO.description()).isEqualTo(itemToAdd.description());
        assertThat(itemDTO.price()).isEqualTo(itemToAdd.price());
        assertThat(itemDTO.amount()).isEqualTo(itemToAdd.amount());
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
                .when().port(port).post("/customers")
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

    @Test
    void givenARequestBody_whenUpdatingAnItem_httpStatusCreatedAndUpdatedItemReturned() {
        UpdateItemDTO itemToUpdate = new UpdateItemDTO(
                "name",
                "testItem",
                6.0,
                30
        );
        Item item = new Item("name", "desc", 20, 10);
        itemRepository.create(item);
        ItemDTO itemDTO =
                RestAssured
                        .given().contentType(JSON).body(itemToUpdate).accept(JSON)
                        .auth().preemptive().basic(admin.getEmailAddress(), admin.getPassword())
                        .when().port(port).put("/items/" + item.getId())
                        .then().assertThat().statusCode(HttpStatus.SC_OK).extract().as(ItemDTO.class);

        assertThat(item.getName()).isEqualTo(itemToUpdate.getName());
        assertThat(item.getDescription()).isEqualTo(itemToUpdate.getDescription());
        assertThat(item.getPrice()).isEqualTo(itemToUpdate.getPrice());
        assertThat(item.getAmount()).isEqualTo(itemToUpdate.getAmount());
    }

}