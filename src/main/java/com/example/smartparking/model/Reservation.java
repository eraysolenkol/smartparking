package com.example.smartparking.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long parkingLocationId;
    private String startTime;
    private String endTime;
    private double totalPrice;
    private String status; // "confirmed", "cancelled", "completed"
    private String createdAt;
    private String updatedAt;

    public Reservation() {
    }

    public Reservation(Long userId, Long parkingLocationId, String startTime, String endTime,
            double totalPrice,
            String status) {
        this.userId = userId;
        this.parkingLocationId = parkingLocationId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalPrice = totalPrice;
        this.status = status;
    }

}
