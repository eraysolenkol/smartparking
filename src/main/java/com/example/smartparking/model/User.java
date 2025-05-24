package com.example.smartparking.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "parkinguser")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Long citizenId;
    private String createdAt;
    private String updatedAt;

    public User() {
    }

    public User(String name, Long citizenId) {
        this.name = name;
        this.citizenId = citizenId;
    }

}
