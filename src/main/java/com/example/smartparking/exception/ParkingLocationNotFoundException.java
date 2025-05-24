package com.example.smartparking.exception;

public class ParkingLocationNotFoundException extends RuntimeException {
    public ParkingLocationNotFoundException(Long id) {
        super("Parking location with ID " + id + " not found.");
    }
}