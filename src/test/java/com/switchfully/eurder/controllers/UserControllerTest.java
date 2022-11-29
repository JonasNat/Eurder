package com.switchfully.eurder.controllers;

import com.switchfully.eurder.domain.Address;
import com.switchfully.eurder.dto.CreateCustomerDTO;
import com.switchfully.eurder.dto.CustomerDTO;
import com.switchfully.eurder.repositories.UserRepository;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {

    @LocalServerPort
    private int port;

    @Test
    void givenARequestBody_whenRegisteringNewCustomer_httpStatusCreatedAndNewCustomerReturned() {

        CreateCustomerDTO userToRegister = new CreateCustomerDTO(
                "firstname",
                "lastname",
                "email@eurder.com",
                "password",
                new Address("street", "housenumber", "0000", "city"),
                "0000000000");

        CustomerDTO customerDTO = RestAssured
                .given().contentType(JSON).body(userToRegister).accept(JSON)
                .when().port(port).post("/users")
                .then().assertThat().statusCode(HttpStatus.SC_CREATED).extract().as(CustomerDTO.class);

        assertThat(customerDTO.getFirstName()).isEqualTo(userToRegister.firstName());
        assertThat(customerDTO.getLastName()).isEqualTo(userToRegister.lastName());
        assertThat(customerDTO.getEmailAddress()).isEqualTo(userToRegister.emailAddress());
        assertThat(customerDTO.getPhoneNumber()).isEqualTo(userToRegister.phoneNumber());
        assertThat(customerDTO.getAddress()).isEqualTo(userToRegister.address());
    }
}