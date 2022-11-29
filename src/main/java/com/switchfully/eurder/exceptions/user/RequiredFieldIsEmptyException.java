package com.switchfully.eurder.exceptions.user;

public class RequiredFieldIsEmptyException extends RuntimeException{
    public RequiredFieldIsEmptyException(String message) {
        super(message);
    }
}
