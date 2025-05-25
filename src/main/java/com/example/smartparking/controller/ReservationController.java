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
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> getReservations() {
        List<Reservation> reservations = reservationService.getReservations();
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<Reservation>> getReservationByUserId(@PathVariable Long id) {
        List<Reservation> reservations = reservationService.getReservationsByUserId(id);
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        Reservation reservation = reservationService.getReservationById(id);
        return ResponseEntity.ok(reservation);
    }

    @GetMapping("/parking/{id}")
    public ResponseEntity<List<Reservation>> getReservationByParkingLocationId(@PathVariable Long id) {
        List<Reservation> reservations = reservationService.getReservationsByParkingLocationId(id);
        return ResponseEntity.ok(reservations);
    }

    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody ReservationRequest request) {
        Reservation reservation = reservationService.makeReservation(request);
        return new ResponseEntity<>(reservation, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reservation> updateReservation(@PathVariable Long id,
            @RequestBody ReservationRequest request) {
        Reservation reservation = reservationService.updateReservation(id, request);
        return ResponseEntity.ok(reservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Reservation> deleteReservation(@PathVariable Long id) {
        Reservation reservation = reservationService.deleteReservation(id);
        return ResponseEntity.ok(reservation);
    }

    @GetMapping("/price/{id}")
    public ResponseEntity<PriceResponse> getReservationPrice(@PathVariable Long id) {
        reservationService.getReservationById(id);
        double price = reservationService.getCurrentPrice(id);
        return ResponseEntity.ok(new PriceResponse(id, price));
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<Reservation> cancelReservation(@PathVariable Long id) {
        Reservation reservation = reservationService.cancelReservation(id);
        return ResponseEntity.ok(reservation);
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<Reservation> completeReservation(@PathVariable Long id) {
        Reservation reservation = reservationService.completeReservation(id);
        return ResponseEntity.ok(reservation);
    }

    @GetMapping("/daily-reservations")
    public ResponseEntity<List<Reservation>> getDailyReservations() {
        List<Reservation> dailyReservations = reservationService.getDailyReservations();
        return ResponseEntity.ok(dailyReservations);
    }

}
