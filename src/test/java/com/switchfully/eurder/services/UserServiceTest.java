package com.switchfully.eurder.services;

import com.switchfully.eurder.domain.Address;
import com.switchfully.eurder.domain.Role;
import com.switchfully.eurder.domain.User;
import com.switchfully.eurder.dto.CreateCustomerDTO;
import com.switchfully.eurder.dto.CustomerDTO;
import com.switchfully.eurder.exceptions.user.CustomerAlreadyExistsException;
import com.switchfully.eurder.exceptions.user.InvalidEmailAddressException;
import com.switchfully.eurder.exceptions.user.RequiredFieldIsEmptyException;
import com.switchfully.eurder.mapper.UserMapper;
import com.switchfully.eurder.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserServiceTest {
    private User customer1;
    private final UserRepository userRepository = new UserRepository();
    private final UserMapper userMapper = new UserMapper();
    private final UserService userService = new UserService(userRepository, userMapper);

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
    void givenARepositoryOfUsers_whenRegisteringANewCustomerWithAnExistingEmailAddress_exceptionIsThrown() {
        userRepository.create(customer1);
        CreateCustomerDTO userToRegister = new CreateCustomerDTO(
                "firstname2",
                "lastname2",
                "email@eurder.com",
                "password",
                new Address("street", "housenumber", "0000", "city"),
                "0000000000"
        );

        assertThatThrownBy(() -> userService.register(userToRegister))
                .isInstanceOf(CustomerAlreadyExistsException.class)
                .hasMessageContaining("Customer with this email already exists");
    }

    @Test
    void givenARepositoryOfUsers_whenRegisteringANewCustomerWithoutAPhoneNumber_exceptionIsThrown() {
        CreateCustomerDTO userToRegister = new CreateCustomerDTO(
                "firstname2",
                "lastname2",
                "email@eurder.com",
                "password",
                new Address("street", "housenumber", "0000", "city"),
                ""
        );

        assertThatThrownBy(() -> userService.register(userToRegister))
                .isInstanceOf(RequiredFieldIsEmptyException.class)
                .hasMessageContaining("Required field missing");
    }

    @Test
    void givenARepositoryOfUsers_whenRegisteringANewCustomerWithAnInvalidEmailAddress_exceptionIsThrown() {
        CreateCustomerDTO userToRegister = new CreateCustomerDTO(
                "firstname2",
                "lastname2",
                "invalidEmail",
                "password",
                new Address("street", "housenumber", "0000", "city"),
                "0000000000"
        );

        assertThatThrownBy(() -> userService.register(userToRegister))
                .isInstanceOf(InvalidEmailAddressException.class)
                .hasMessageContaining("Invalid email");
    }

    @Test
    void givenARepositoryOfUsers_whenGettingAllCustomers_returnsAllUsersWhoAreCustomers() {
        User customer2 = new User(
                "firstname2",
                "lastname2",
                "email2@eurder.com",
                "password",
                new Address("street", "housenumber", "0000", "city"),
                "0000000000",
                Role.CUSTOMER);
        CustomerDTO customer1DTO = userMapper.toDto(userRepository.create(customer1));
        CustomerDTO customer2DTO = userMapper.toDto(userRepository.create(customer2));

        assertThat(userService.getAllCustomers()).containsExactlyInAnyOrder(customer1DTO, customer2DTO);
    }

}