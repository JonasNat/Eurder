package com.switchfully.eurder.dto;

import com.switchfully.eurder.domain.Address;
import com.switchfully.eurder.domain.Role;

public class UserDTO {
    private String firstName;
    private String lastName;
    private String emailAddress;
    private Address address;
    private String phoneNumber;
    private Role role;

    public UserDTO(String firstName, String lastName, String emailAddress, Address address, String phoneNumber, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }




}
