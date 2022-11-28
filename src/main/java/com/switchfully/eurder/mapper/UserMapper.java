package com.switchfully.eurder.mapper;

import com.switchfully.eurder.domain.User;
import com.switchfully.eurder.dto.CreateCustomerDTO;
import com.switchfully.eurder.dto.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDTO toDto(User user) {
        return new UserDTO(user.getFirstName(),
                user.getLastName(),
                user.getEmailAddress(),
                user.getAddress(),
                user.getPhoneNumber(),
                user.getRole());
    }

    public User toUser(CreateCustomerDTO userToRegister) {
        return new User(userToRegister.getFirstName(),
                userToRegister.getLastName(),
                userToRegister.getEmailAddress(),
                userToRegister.getAddress(),
                userToRegister.getPhoneNumber(),
                userToRegister.getRole());
    }


}
