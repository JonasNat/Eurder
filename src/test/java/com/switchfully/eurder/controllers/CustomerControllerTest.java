package com.switchfully.eurder.controllers;

import com.switchfully.eurder.domain.Address;
import com.switchfully.eurder.domain.Role;
import com.switchfully.eurder.domain.User;
import com.switchfully.eurder.dto.CreateCustomerDTO;
import com.switchfully.eurder.dto.CustomerDTO;
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
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomerControllerTest {
    private User admin;
    private User customer;
    @Autowired
    private final UserRepository userRepository = new UserRepository();
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
        userRepository.create(admin);
        userRepository.create(customer);
    }

    @Test
    void givenARequestBody_whenRegisteringNewCustomer_httpStatusCreatedAndNewCustomerReturned() {
        CreateCustomerDTO userToRegister = new CreateCustomerDTO(
                "firstname",
                "lastname",
                "email@eurder.com",
                "password",
                new Address("street", "housenumber", "0000", "city"),
                "0000000000");

        CustomerDTO customerDTO =
                RestAssured
                .given().contentType(JSON).body(userToRegister).accept(JSON)
                .when().port(port).post("/customers")
                .then().assertThat().statusCode(HttpStatus.SC_CREATED).extract().as(CustomerDTO.class);

        assertThat(customerDTO.getFirstName()).isEqualTo(userToRegister.firstName());
        assertThat(customerDTO.getLastName()).isEqualTo(userToRegister.lastName());
        assertThat(customerDTO.getEmailAddress()).isEqualTo(userToRegister.emailAddress());
        assertThat(customerDTO.getPhoneNumber()).isEqualTo(userToRegister.phoneNumber());
        assertThat(customerDTO.getAddress()).isEqualTo(userToRegister.address());
    }

    @Test
    void givenARequestBodyWithInvalidEmail_whenRegisteringNewCustomer_httpStatusBadRequest() {
        CreateCustomerDTO userToRegister = new CreateCustomerDTO(
                "firstname",
                "lastname",
                "invalidEmail",
                "password",
                new Address("street", "housenumber", "0000", "city"),
                "0000000000");

        RestAssured
                .given().contentType(JSON).body(userToRegister).accept(JSON)
                .when().port(port).post("/customers")
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    void givenARepositoryWithUsers_whenGettingAllCustomers_httpStatusOk() {
        RestAssured
                .given()
                .auth().preemptive().basic(admin.getEmailAddress(), admin.getPassword())
                .when().port(port).get("/customers")
                .then().assertThat().statusCode(HttpStatus.SC_OK);
    }

    @Test
    void givenARepositoryWithUsers_whenGettingAllCustomersWithoutBeingAdmin_httpStatusForbidden() {
        RestAssured
                .given()
                .auth().preemptive().basic(customer.getEmailAddress(), customer.getPassword())
                .when().port(port).get("/customers")
                .then().assertThat().statusCode(HttpStatus.SC_FORBIDDEN);
    }
}