package com.example.smartparking.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import com.example.smartparking.service.ParkingLogService;
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
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;
    private final ParkingLogService parkingLogService;

    public ReservationController(ReservationService reservationService, ParkingLogService parkingLogService) {
        this.reservationService = reservationService;
        this.parkingLogService = parkingLogService;
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> getReservations(HttpServletRequest request) {
        List<Reservation> reservations = reservationService.getReservations();
        String ipAddress = request.getRemoteAddr();
        parkingLogService.log("GET", ipAddress, "Reservation", "/api/reservations");
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<Reservation>> getReservationByUserId(HttpServletRequest request, @PathVariable Long id) {
        List<Reservation> reservations = reservationService.getReservationsByUserId(id);
        String ipAddress = request.getRemoteAddr();
        parkingLogService.log("GET", ipAddress, "Reservation", "/api/reservations/user/" + id);
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(HttpServletRequest request, @PathVariable Long id) {
        Reservation reservation = reservationService.getReservationById(id);
        String ipAddress = request.getRemoteAddr();
        parkingLogService.log("GET", ipAddress, "Reservation", "/api/reservations/" + id);
        return ResponseEntity.ok(reservation);
    }

    @GetMapping("/parking/{id}")
    public ResponseEntity<List<Reservation>> getReservationByParkingLocationId(HttpServletRequest request,
            @PathVariable Long id) {
        List<Reservation> reservations = reservationService.getReservationsByParkingLocationId(id);
        String ipAddress = request.getRemoteAddr();
        parkingLogService.log("GET", ipAddress, "Reservation", "/api/reservations/parking/" + id);
        return ResponseEntity.ok(reservations);
    }

    @PostMapping
    public ResponseEntity<Reservation> createReservation(HttpServletRequest httpRequest,
            @RequestBody ReservationRequest request) {
        Reservation reservation = reservationService.makeReservation(request);
        String ipAddress = httpRequest.getRemoteAddr();
        parkingLogService.log("POST", ipAddress, "Reservation", "/api/reservations");
        return new ResponseEntity<>(reservation, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reservation> updateReservation(HttpServletRequest httpRequest, @PathVariable Long id,
            @RequestBody ReservationRequest request) {
        Reservation reservation = reservationService.updateReservation(id, request);
        String ipAddress = httpRequest.getRemoteAddr();
        parkingLogService.log("PUT", ipAddress, "Reservation", "/api/reservations/" + id);
        return ResponseEntity.ok(reservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Reservation> deleteReservation(HttpServletRequest request, @PathVariable Long id) {
        Reservation reservation = reservationService.deleteReservation(id);
        String ipAddress = request.getRemoteAddr();
        parkingLogService.log("DELETE", ipAddress, "Reservation", "/api/reservations/" + id);
        return ResponseEntity.ok(reservation);
    }

    @GetMapping("/price/{id}")
    public ResponseEntity<PriceResponse> getReservationPrice(HttpServletRequest request, @PathVariable Long id) {
        reservationService.getReservationById(id);
        double price = reservationService.getCurrentPrice(id);
        String ipAddress = request.getRemoteAddr();
        parkingLogService.log("GET", ipAddress, "Reservation", "/api/reservations/price/" + id);
        return ResponseEntity.ok(new PriceResponse(id, price));
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<Reservation> cancelReservation(HttpServletRequest request, @PathVariable Long id) {
        Reservation reservation = reservationService.cancelReservation(id);
        String ipAddress = request.getRemoteAddr();
        parkingLogService.log("PUT", ipAddress, "Reservation", "/api/reservations/" + id + "/cancel");
        return ResponseEntity.ok(reservation);
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<Reservation> completeReservation(HttpServletRequest request, @PathVariable Long id) {
        Reservation reservation = reservationService.completeReservation(id);
        String ipAddress = request.getRemoteAddr();
        parkingLogService.log("PUT", ipAddress, "Reservation", "/api/reservations/" + id + "/complete");
        return ResponseEntity.ok(reservation);
    }

    @GetMapping("/daily-reservations")
    public ResponseEntity<List<Reservation>> getDailyReservations(HttpServletRequest request) {
        List<Reservation> dailyReservations = reservationService.getDailyReservations();
        String ipAddress = request.getRemoteAddr();
        parkingLogService.log("GET", ipAddress, "Reservation", "/api/reservations/daily-reservations");
        return ResponseEntity.ok(dailyReservations);
    }

}
