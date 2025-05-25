package com.example.smartparking.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.smartparking.model.Reservation;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByUserId(Long userId);

    List<Reservation> findByParkingLocationId(Long parkingLocationId);

    List<Reservation> findByCreatedAtStartingWith(String createdAt);

}
