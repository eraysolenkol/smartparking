package com.example.smartparking.dto;

public class ParkingLocationRequest {
    public String name;
    public String address;
    public Boolean isAvailable;
    public double pricePerHour;
    public int totalSpots;
    public int availableSpots;
    public String imageUrl;
    public String description;

    public ParkingLocationRequest(String name, String address, Boolean isAvailable, double pricePerHour,
            int totalSpots, int availableSpots, String imageUrl, String description) {
        this.name = name;
        this.address = address;
        this.isAvailable = isAvailable;
        this.pricePerHour = pricePerHour;
        this.totalSpots = totalSpots;
        this.availableSpots = availableSpots;
        this.imageUrl = imageUrl;
        this.description = description;
    }
}
