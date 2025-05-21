package com.example.smartparking.dto;

public class PriceResponse {
    public Long reservationId;
    public double price;

    public PriceResponse(Long reservationId, double price) {
        this.reservationId = reservationId;
        this.price = price;
    }
}
