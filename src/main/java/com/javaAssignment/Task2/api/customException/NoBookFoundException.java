package com.javaAssignment.Task2.api.customException;

public class NoBookFoundException extends RuntimeException{
    public NoBookFoundException(String message) {
        super(message);
    }
}
