package com.example.smartparking.dto;

public class ReservationRequest {
    public Long userId;
    public Long parkingLocationId;
    public String startTime;
    public String endTime;
    public double totalPrice;
    public String status; // "confirmed", "cancelled", "completed"
}
