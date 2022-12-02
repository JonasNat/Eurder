package com.switchfully.eurder.services;

import com.switchfully.eurder.domain.Role;
import com.switchfully.eurder.dto.CreateCustomerDTO;
import com.switchfully.eurder.dto.CustomerDTO;
import com.switchfully.eurder.exceptions.user.CustomerAlreadyExistsException;
import com.switchfully.eurder.exceptions.user.CustomerNotFoundException;
import com.switchfully.eurder.mapper.UserMapper;
import com.switchfully.eurder.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository repository;
    private final UserMapper mapper;

    public UserService(UserRepository repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<CustomerDTO> getAllCustomers() {
        return mapper.toDto(repository.getAll().stream().filter(user -> user.getRole().equals(Role.CUSTOMER)).toList());
    }

    public CustomerDTO findById(String id) {
        return mapper.toDto(repository.findById(id).orElseThrow(() -> new CustomerNotFoundException("Customer not found")));
    }

    public CustomerDTO register(CreateCustomerDTO userToRegister) {
        if (repository.getAll().stream().anyMatch(user -> user.getEmailAddress().equals(userToRegister.emailAddress()))) {
            throw new CustomerAlreadyExistsException("Customer with this email already exists");
        }
        return mapper.toDto(repository.create(mapper.toUser(userToRegister)));
    }
}
