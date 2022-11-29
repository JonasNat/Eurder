package com.switchfully.eurder.dto;

import com.switchfully.eurder.domain.Address;

public record CreateCustomerDTO(
        String firstName,
        String lastName,
        String emailAddress,
        Address address,
        String phoneNumber) {
}
