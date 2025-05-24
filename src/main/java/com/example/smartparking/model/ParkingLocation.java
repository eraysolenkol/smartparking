package com.example.smartparking.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import jakarta.persistence.GenerationType;

@Entity
@Data
public class ParkingLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;
    private String imageUrl;
    private String description;
    private Boolean isAvailable;
    private double pricePerHour;
    private int totalSpots;
    private int availableSpots;
    private String createdAt;
    private String updatedAt;

    public ParkingLocation() {
    }

    public ParkingLocation(String name, String address, Boolean isAvailable, double pricePerHour, int totalSpots,
            int availableSpots, String imageUrl, String description) {
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
