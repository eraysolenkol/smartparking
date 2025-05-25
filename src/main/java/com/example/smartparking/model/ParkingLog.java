package com.example.smartparking.model;

import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@Data
public class ParkingLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String action;
    private String ipAddress;
    private String entity;
    private String apiEndpoint;
    private String createdAt;

    public ParkingLog() {
    }

    public ParkingLog(String action, String ipAddress, String entity, String apiEndpoint,
            String createdAt) {
        this.action = action;
        this.ipAddress = ipAddress;
        this.entity = entity;
        this.apiEndpoint = apiEndpoint;
        this.createdAt = createdAt;
    }

}
