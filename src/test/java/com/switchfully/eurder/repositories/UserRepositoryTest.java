package com.switchfully.eurder.repositories;

import com.switchfully.eurder.domain.Address;
import com.switchfully.eurder.domain.Role;
import com.switchfully.eurder.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserRepositoryTest {
    private User customer1;
    private final UserRepository userRepository = new UserRepository();

    @BeforeEach
    void setup() {
        customer1 = new User(
                "firstname",
                "lastname",
                "email@eurder.com",
                "password",
                new Address("street", "housenumber", "0000", "city"),
                "0000000000",
                Role.CUSTOMER);
    }

    @Test
    void givenARepositoryOfUsers_whenCreatingANewCustomer_customerIsAddedToTheRepository() {
        userRepository.create(customer1);

        assertThat(userRepository.getAll()).contains(customer1);
    }
}