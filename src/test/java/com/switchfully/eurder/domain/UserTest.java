package com.switchfully.eurder.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class UserTest {
    @Test
    void givenAUserWithPassword_whenCheckingForMatchWithCorrectPassword_returnsTrue() {
        User user = new User(
                "firstname",
                "lastname",
                "email@eurder.com",
                "password",
                new Address("street", "housenumber", "0000", "city"),
                "0000000000",
                Role.CUSTOMER
        );
        String correctPassword = "password";
        String incorrectPassword = "incorrectPassword";

        assertThat(user.doesPasswordMatch(correctPassword)).isTrue();
        assertThat(user.doesPasswordMatch(incorrectPassword)).isFalse();
    }
}