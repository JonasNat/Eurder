package com.switchfully.eurder.exceptions;

public class RequiredFieldIsEmptyException extends RuntimeException{
    public RequiredFieldIsEmptyException(String message) {
        super(message);
    }
}
