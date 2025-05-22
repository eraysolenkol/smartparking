package com.example.smartparking.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import com.example.smartparking.service.ReservationService;
import com.example.smartparking.dto.PriceResponse;
import com.example.smartparking.dto.ReservationRequest;
import com.example.smartparking.model.Reservation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public List<Reservation> getReservations() {
        return reservationService.getReservations();
    }

    @GetMapping("/user/{id}")
    public List<Reservation> getReservationByUserId(@PathVariable Long id) {
        return reservationService.getReservationsByUserId(id);
    }

    @GetMapping("/{id}")
    public Reservation getReservationById(@PathVariable Long id) {
        return reservationService.getReservationById(id);
    }

    @GetMapping("/parking/{id}")
    public List<Reservation> getReservationByParkingLocationId(@PathVariable Long id) {
        return reservationService.getReservationsByParkingLocationId(id);
    }

    @PostMapping
    public ResponseEntity<?> createReservation(@RequestBody ReservationRequest request) {
        try {
            Reservation reservation = reservationService.makeReservation(request);
            return ResponseEntity.ok(reservation);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(errorResponse);
        }
    }


    @PutMapping("/{id}")
    public Reservation updateReservation(@PathVariable Long id, @RequestBody ReservationRequest request) {
        Reservation reservation = reservationService.updateReservation(id, request).orElse(null);
        if (reservation != null) {
            reservationService.updateReservation(id, request);
            return reservation;
        } else {
            return null;
        }
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
    }

    @GetMapping("/price/{id}")
    public ResponseEntity<?> getReservationPrice(@PathVariable Long id) {
        Reservation reservation = reservationService.getReservationById(id);
        if (reservation != null) {
            double price = reservationService.getCurrentPrice(id);
            return ResponseEntity.ok(new PriceResponse(id, price));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Reservation not found"));
        }
    }

    @PutMapping("/{id}/cancel")
    public Optional<Reservation> cancelReservation(@PathVariable Long id) {
        Reservation reservation = reservationService.cancelReservation(id);
        return Optional.ofNullable(reservation);
    }

    @PutMapping("/{id}/complete")
    public Optional<Reservation> completeReservation(@PathVariable Long id) {
        Reservation reservation = reservationService.completeReservation(id);
        return Optional.ofNullable(reservation);
    }

}
