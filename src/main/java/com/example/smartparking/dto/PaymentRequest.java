package com.example.smartparking.dto;

public class PaymentRequest {
    public Long reservationId;
    public double amount;
    public boolean isCard;
    public String status;

    public PaymentRequest() {
    }

    public PaymentRequest(Long reservationId, double amount, boolean isCard, String status) {
        this.reservationId = reservationId;
        this.amount = amount;
        this.isCard = isCard;
        this.status = status;
    }
}