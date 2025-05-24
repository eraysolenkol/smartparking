package com.example.smartparking.exception;

public class ParkingLocationFullException extends RuntimeException {
    public ParkingLocationFullException(Long id) {
        super("Parking location with ID " + id + " is full.");
    }

    public ParkingLocationFullException(String message) {
        super(message);
    }

    public ParkingLocationFullException() {
        super("Parking location is full.");
    }

}
