package com.switchfully.eurder.domain;

import com.switchfully.eurder.exceptions.user.InvalidEmailAddressException;
import com.switchfully.eurder.exceptions.user.RequiredFieldIsEmptyException;

import java.util.Objects;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {
    private final String id;
    private final String firstName;
    private final String lastName;
    private final String emailAddress;
    private final String password;
    private final Address address;
    private final String phoneNumber;
    private final Role role;
    private static final String EMAIL_REGEX = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    public User(String firstName, String lastName, String emailAddress, String password, Address address, String phoneNumber, Role role) {
        id = UUID.randomUUID().toString();
        this.firstName = firstName;
        this.lastName = validateField(lastName);
        this.emailAddress = validateEmail(emailAddress);
        this.password = validateField(password);
        this.address = address;
        this.phoneNumber = validateField(phoneNumber);
        this.role = role == null ? Role.CUSTOMER : role;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public Address getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Role getRole() {
        return role;
    }

    private String validateEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            throw new InvalidEmailAddressException("Invalid email address");
        }
        return email;
    }

    private String validateField(String field) {
        if (field == null || field.isEmpty()) {
            throw new RequiredFieldIsEmptyException("Required field missing");
        }
        return field;
    }

    public boolean doesPasswordMatch(String password) {
        return password.equals(this.password);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return emailAddress.equals(user.emailAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(emailAddress);
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                '}';
    }
}
