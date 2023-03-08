package com.springdemo.exceptions.parts;

public class CustomUnauthorizedException extends Exception {
    public CustomUnauthorizedException(String message) {
        super(message);
    }
}
