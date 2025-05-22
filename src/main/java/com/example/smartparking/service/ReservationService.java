package com.example.smartparking.service;

import org.springframework.stereotype.Service;
import com.example.smartparking.dto.ReservationRequest;
import com.example.smartparking.model.ParkingLocation;
import com.example.smartparking.model.Reservation;
import com.example.smartparking.repository.ReservationRepository;
import java.util.List;
import java.util.Optional;
import com.example.smartparking.dto.ParkingLocationRequest;
import com.example.smartparking.dto.PaymentRequest;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ParkingLocationService parkingLocationService;
    private final PaymentService paymentService;

    public ReservationService(ReservationRepository reservationRepository,
            ParkingLocationService parkingLocationService, PaymentService paymentService) {
        this.reservationRepository = reservationRepository;
        this.parkingLocationService = parkingLocationService;
        this.paymentService = paymentService;
    }

    public Reservation createReservation(ReservationRequest request) {
        Reservation reservation = new Reservation(
                request.userId,
                request.parkingLocationId,
                request.startTime,
                request.endTime,
                request.totalPrice,
                request.status);
        return reservationRepository.save(reservation);
    }

    public List<Reservation> getReservationsByUserId(Long userId) {
        return reservationRepository.findByUserId(userId);
    }

    public List<Reservation> getReservationsByParkingLocationId(Long parkingLocationId) {
        return reservationRepository.findByParkingLocationId(parkingLocationId);
    }

    public Reservation getReservationById(Long reservationId) {
        return reservationRepository.findById(reservationId).orElse(null);
    }

    public Optional<Reservation> updateReservation(Long reservationId, ReservationRequest request) {
        return reservationRepository.findById(reservationId).map(e -> {
            e.setUserId(request.userId);
            e.setParkingLocationId(request.parkingLocationId);
            e.setStartTime(request.startTime);
            e.setEndTime(request.endTime);
            e.setTotalPrice(request.totalPrice);
            e.setStatus(request.status);
            return reservationRepository.save(e);
        });
    }

    public Reservation deleteReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElse(null);
        if (reservation != null) {
            reservationRepository.delete(reservation);
        }
        return reservation;
    }

    public List<Reservation> getReservations() {
        return reservationRepository.findAll();
    }

    public Reservation makeReservation(ReservationRequest request) {
        ParkingLocation parkingLocation = parkingLocationService
                .getParkingLocationById(request.parkingLocationId);
        if (parkingLocation == null) {
            throw new IllegalArgumentException("Parking location is not available");
        } else if (parkingLocation.getAvailableSpots() <= 0) {
            throw new IllegalArgumentException("Parking location is full");
        }

        Reservation reservation = new Reservation();
        reservation.setUserId(request.userId);
        reservation.setParkingLocationId(request.parkingLocationId);
        reservation.setStartTime(request.startTime);
        reservation.setStatus("confirmed");
        reservation.setTotalPrice(0);
        reservation.setEndTime(null);

        parkingLocation.setAvailableSpots(parkingLocation.getAvailableSpots() - 1);

        parkingLocationService.updateParkingLocation(parkingLocation.getId(),
                new ParkingLocationRequest(
                        parkingLocation.getName(),
                        parkingLocation.getAddress(),
                        parkingLocation.getIsAvailable(),
                        parkingLocation.getPricePerHour(),
                        parkingLocation.getTotalSpots(),
                        parkingLocation.getAvailableSpots(),
                        parkingLocation.getImageUrl(),
                        parkingLocation.getDescription()));

        return reservationRepository.save(reservation);
    }

    public double getCurrentPrice(Long reservationId) {
        double cost = 0.0;
        Reservation reservation = reservationRepository.findById(reservationId).orElse(null);

        if (reservation.getStatus().equals("cancelled") || reservation.getStatus().equals("completed")) {
            return reservation.getTotalPrice();
        }

        ParkingLocation parkingLocation = parkingLocationService
                .getParkingLocationById(reservation.getParkingLocationId());

        LocalDateTime currentTime = LocalDateTime.now();
        if (reservation != null) {
            Duration duration = Duration.between(LocalDateTime.parse(reservation.getStartTime()), currentTime);
            long hours = duration.toHours();
            long minutes = duration.toMinutes() % 60;
            if (minutes <= 0) {
                cost = hours * parkingLocation.getPricePerHour();
            } else {
                cost = (hours + 1) * parkingLocation.getPricePerHour();
            }
        }
        return cost;
    }

    public Reservation cancelReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElse(null);
        double cost = getCurrentPrice(reservationId);
        if (reservation != null) {
            if (cost > 0) {
                reservation.setTotalPrice(cost);
            }
            reservation.setStatus("cancelled");
            reservationRepository.save(reservation);
        }
        return reservation;
    }

    public Reservation completeReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElse(null);
        ParkingLocation parkingLocation = parkingLocationService
                .getParkingLocationById(reservation.getParkingLocationId());
        if (reservation != null) {
            reservation.setTotalPrice(getCurrentPrice(reservationId));
            reservation.setStatus("completed");
            reservation.setEndTime(LocalDateTime.now().toString().split("\\.")[0]);
            reservationRepository.save(reservation);
            parkingLocation.setAvailableSpots(parkingLocation.getAvailableSpots() + 1);
            parkingLocationService.updateParkingLocation(parkingLocation.getId(),
                    new ParkingLocationRequest(
                            parkingLocation.getName(),
                            parkingLocation.getAddress(),
                            parkingLocation.getIsAvailable(),
                            parkingLocation.getPricePerHour(),
                            parkingLocation.getTotalSpots(),
                            parkingLocation.getAvailableSpots(),
                            parkingLocation.getImageUrl(),
                            parkingLocation.getDescription()));
        }
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.reservationId = reservation.getId();
        paymentRequest.isCard = false;
        paymentRequest.status = "waiting";
        paymentRequest.amount = reservation.getTotalPrice();
        paymentService.makePayment(paymentRequest);
        return reservation;
    }

}
