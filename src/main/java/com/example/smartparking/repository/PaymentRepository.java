package com.example.smartparking.repository;

import com.example.smartparking.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.smartparking.model.Reservation;
import java.util.Optional;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByReservation(Reservation reservation);

    List<Payment> findByReservationId(Long reservationId);

    Optional<Payment> findById(Long id);

    List<Payment> findByCreatedAtStartingWith(String createdAt);

}
