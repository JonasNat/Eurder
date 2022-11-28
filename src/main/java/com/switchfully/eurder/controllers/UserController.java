package com.switchfully.eurder.controllers;

import com.switchfully.eurder.dto.CreateCustomerDTO;
import com.switchfully.eurder.dto.UserDTO;
import com.switchfully.eurder.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO register(@RequestBody CreateCustomerDTO userToRegister) {
        return userService.register(userToRegister);
    }
}
