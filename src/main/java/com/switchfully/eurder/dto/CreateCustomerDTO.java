package com.switchfully.eurder.dto;

import com.switchfully.eurder.domain.Address;

public record CreateCustomerDTO(
        String firstName,
        String lastName,
        String emailAddress,

        String password,
        Address address,
        String phoneNumber) {
}
