package com.switchfully.eurder.exceptions.security;

public class UnauthorizedException extends RuntimeException{
    public UnauthorizedException(String message) {
        super(message);
    }
}
