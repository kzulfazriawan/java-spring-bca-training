package com.springdemo.exceptions.parts;

public class CustomDuplicateError extends Exception{
    public CustomDuplicateError(String message){
        super(message);
    }
}
