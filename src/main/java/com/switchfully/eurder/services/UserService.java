package com.switchfully.eurder.services;

import com.switchfully.eurder.dto.CreateCustomerDTO;
import com.switchfully.eurder.dto.CustomerDTO;
import com.switchfully.eurder.exceptions.CustomerAlreadyExistsException;
import com.switchfully.eurder.mapper.UserMapper;
import com.switchfully.eurder.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository repository;
    private final UserMapper mapper;

    public UserService(UserRepository repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public CustomerDTO register(CreateCustomerDTO userToRegister) {
        if (repository.getAll().stream().anyMatch(user -> user.getEmailAddress().equals(userToRegister.emailAddress()))) {
            throw new CustomerAlreadyExistsException("Customer with this email already exists");
        }
        return mapper.toDto(repository.create(mapper.toUser(userToRegister)));
    }
}
