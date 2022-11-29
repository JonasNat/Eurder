package com.switchfully.eurder.exceptions.user;

public class InvalidEmailAddressException extends RuntimeException{
    public InvalidEmailAddressException(String message) {
        super(message);
    }
}
