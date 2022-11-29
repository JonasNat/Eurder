package com.switchfully.eurder.exceptions;

public class InvalidEmailAddressException extends RuntimeException{
    public InvalidEmailAddressException(String message) {
        super(message);
    }
}
