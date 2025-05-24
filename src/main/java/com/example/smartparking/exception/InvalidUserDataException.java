package com.example.smartparking.exception;

public class InvalidUserDataException extends RuntimeException {
    public InvalidUserDataException(String message) {
        super("Invalid user data: " + message);
    }
}