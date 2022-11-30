package com.switchfully.eurder.mapper;

import com.switchfully.eurder.domain.Role;
import com.switchfully.eurder.domain.User;
import com.switchfully.eurder.dto.CreateCustomerDTO;
import com.switchfully.eurder.dto.CustomerDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {
    public CustomerDTO toDto(User user) {
        return new CustomerDTO(
                user.getFirstName(),
                user.getLastName(),
                user.getEmailAddress(),
                user.getAddress(),
                user.getPhoneNumber()
        );
    }

    public User toUser(CreateCustomerDTO userToRegister) {
        return new User(
                userToRegister.firstName(),
                userToRegister.lastName(),
                userToRegister.emailAddress(),
                userToRegister.password(),
                userToRegister.address(),
                userToRegister.phoneNumber(),
                Role.CUSTOMER
        );
    }

    public List<CustomerDTO> toDto(List<User> customers) {
        return customers.stream().map(this::toDto).toList();
    }


}
