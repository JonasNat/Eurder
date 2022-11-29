package com.switchfully.eurder.domain;

import com.switchfully.eurder.exceptions.RequiredFieldIsEmptyException;

public record Address(String streetName, String houseNumber, String postalCode, String city) {
    public Address(String streetName, String houseNumber, String postalCode, String city) {
        this.streetName = validateField(streetName);
        this.houseNumber = validateField(houseNumber);
        this.postalCode = validateField(postalCode);
        this.city = validateField(city);
    }

    private String validateField(String field) {
        if (field == null || field.isEmpty()) {
            throw new RequiredFieldIsEmptyException("Required field missing");
        }
        return field;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return streetName.equals(address.streetName) && houseNumber.equals(address.houseNumber) && postalCode.equals(address.postalCode) && city.equals(address.city);
    }

}
