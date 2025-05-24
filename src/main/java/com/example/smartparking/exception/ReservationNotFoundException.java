package com.example.smartparking.exception;

public class ReservationNotFoundException extends RuntimeException {
    public ReservationNotFoundException(Long id) {
        super("Reservation with ID " + id + " not found.");
    }

    public ReservationNotFoundException() {
        super("Reservation not found.");
    }

    public ReservationNotFoundException(String string) {
        super(string);
    }
}