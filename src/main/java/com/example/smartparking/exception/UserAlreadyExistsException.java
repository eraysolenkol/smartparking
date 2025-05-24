package com.example.smartparking.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(Long citizenId) {
        super("User with citizen ID " + citizenId + " already exists.");
    }

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}