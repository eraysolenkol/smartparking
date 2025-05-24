package com.example.smartparking.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Data
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservation;

    private double amount;
    private Boolean isCard;
    private String status;
    private String createdAt;
    private String updatedAt;

    public Payment() {
    }

    public Payment(Reservation reservation, double amount, Boolean isCard, String status) {
        this.reservation = reservation;
        this.amount = amount;
        this.isCard = isCard;
        this.status = status;
    }

}
