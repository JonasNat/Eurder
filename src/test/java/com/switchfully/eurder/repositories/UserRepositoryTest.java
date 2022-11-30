package com.switchfully.eurder.repositories;

import com.switchfully.eurder.domain.Address;
import com.switchfully.eurder.domain.Role;
import com.switchfully.eurder.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

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

    @Test
    void givenARepositoryOfUsers_whenGettingAllUsers_returnsAllUsers() {
        User customer2 = new User(
                "firstname2",
                "lastname2",
                "email2@eurder.com",
                "password",
                new Address("street", "housenumber", "0000", "city"),
                "0000000000",
                Role.CUSTOMER);
        userRepository.create(customer1);
        userRepository.create(customer2);
        User admin = userRepository.findByEmailAddress("jonas@eurder.com").get();

        assertThat(userRepository.getAll()).containsExactlyInAnyOrder(customer1, customer2, admin);
    }

    @Test
    void givenARepositoryOfUsers_whenFindingUserByEmail_returnsCorrectUser() {
        userRepository.create(customer1);

        assertThat(userRepository.findByEmailAddress("email@eurder.com")).hasValueSatisfying(customer -> assertThat(customer.getLastName()).isEqualTo("lastname"));
    }
}