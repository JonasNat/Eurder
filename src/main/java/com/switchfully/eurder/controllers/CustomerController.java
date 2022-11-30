package com.switchfully.eurder.controllers;

import com.switchfully.eurder.domain.Role;
import com.switchfully.eurder.dto.CreateCustomerDTO;
import com.switchfully.eurder.dto.CustomerDTO;
import com.switchfully.eurder.security.SecurityService;
import com.switchfully.eurder.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private final UserService userService;
    private final SecurityService securityService;

    public CustomerController(UserService userService, SecurityService securityService) {
        this.userService = userService;
        this.securityService = securityService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerDTO> getAllCustomers(@RequestHeader String authorization) {
        securityService.validateAuthorization(authorization, Role.ADMIN);
        return userService.getAllCustomers();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO register(@RequestBody CreateCustomerDTO userToRegister) {
        return userService.register(userToRegister);
    }
}
