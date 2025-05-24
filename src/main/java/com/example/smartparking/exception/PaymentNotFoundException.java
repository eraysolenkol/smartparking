package com.example.smartparking.exception;

public class PaymentNotFoundException extends RuntimeException {
    public PaymentNotFoundException(Long id) {
        super("Payment with ID " + id + " not found.");
    }

    public PaymentNotFoundException() {
        super("Payment not found.");
    }
}