package com.example.smartparking.service;

import org.springframework.stereotype.Service;
import com.example.smartparking.dto.ReservationRequest;
import com.example.smartparking.exception.UserNotFoundException;
import com.example.smartparking.model.ParkingLocation;
import com.example.smartparking.model.Reservation;
import com.example.smartparking.repository.ReservationRepository;
import java.util.List;
import com.example.smartparking.dto.ParkingLocationRequest;
import com.example.smartparking.dto.PaymentRequest;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.example.smartparking.exception.ParkingLocationFullException;
import com.example.smartparking.exception.ParkingLocationNotFoundException;
import com.example.smartparking.exception.ReservationNotFoundException;
import com.example.smartparking.repository.UserRepository;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ParkingLocationService parkingLocationService;
    private final PaymentService paymentService;
    private final UserRepository userRepository;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    public ReservationService(ReservationRepository reservationRepository,
            ParkingLocationService parkingLocationService, PaymentService paymentService,
            UserRepository userRepository) {
        this.reservationRepository = reservationRepository;
        this.parkingLocationService = parkingLocationService;
        this.paymentService = paymentService;
        this.userRepository = userRepository;
    }

    public Reservation createReservation(ReservationRequest request) {
        String now = LocalDateTime.now().format(formatter);
        userRepository.findById(request.userId)
                .orElseThrow(() -> new UserNotFoundException(request.userId));
        Reservation reservation = new Reservation(
                request.userId,
                request.parkingLocationId,
                request.startTime,
                request.endTime,
                request.totalPrice,
                request.status);
        reservation.setCreatedAt(now);
        reservation.setUpdatedAt(now);
        return reservationRepository.save(reservation);
    }

    public List<Reservation> getReservationsByUserId(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        List<Reservation> reservations = reservationRepository.findByUserId(userId);
        if (reservations.isEmpty()) {
            throw new ReservationNotFoundException();
        }
        return reservations;
    }

    public List<Reservation> getReservationsByParkingLocationId(Long parkingLocationId) {
        List<Reservation> reservations = reservationRepository.findByParkingLocationId(parkingLocationId);
        if (reservations.isEmpty()) {
            throw new ReservationNotFoundException();
        }
        return reservations;
    }

    public Reservation getReservationById(Long reservationId) {
        return reservationRepository.findById(reservationId).orElseThrow(
                () -> new ReservationNotFoundException(reservationId));
    }

    public Reservation updateReservation(Long reservationId, ReservationRequest request) {
        String now = LocalDateTime.now().format(formatter);
        userRepository.findById(request.userId)
                .orElseThrow(() -> new UserNotFoundException(request.userId));

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationNotFoundException(reservationId));

        reservation.setUserId(request.userId);
        reservation.setParkingLocationId(request.parkingLocationId);
        reservation.setStartTime(request.startTime);
        reservation.setEndTime(request.endTime);
        reservation.setTotalPrice(request.totalPrice);
        reservation.setStatus(request.status);
        reservation.setUpdatedAt(now);

        return reservationRepository.save(reservation);
    }

    public Reservation deleteReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(
                () -> new ReservationNotFoundException(reservationId));

        reservationRepository.delete(reservation);

        return reservation;
    }

    public List<Reservation> getReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        if (reservations.isEmpty()) {
            throw new ReservationNotFoundException();
        }
        return reservations;
    }

    public Reservation makeReservation(ReservationRequest request) {
        String now = LocalDateTime.now().format(formatter);
        ParkingLocation parkingLocation = parkingLocationService.getParkingLocationById(request.parkingLocationId);

        if (parkingLocation == null) {
            throw new ParkingLocationNotFoundException(request.parkingLocationId);
        }
        if (parkingLocation.getAvailableSpots() <= 0) {
            throw new ParkingLocationFullException(request.parkingLocationId);
        }

        userRepository.findById(request.userId)
                .orElseThrow(() -> new UserNotFoundException(request.userId));

        Reservation reservation = new Reservation();
        reservation.setUserId(request.userId);
        reservation.setParkingLocationId(request.parkingLocationId);
        reservation.setStartTime(request.startTime);
        reservation.setStatus("confirmed");
        reservation.setTotalPrice(0);
        reservation.setEndTime(null);
        reservation.setCreatedAt(now);
        reservation.setUpdatedAt(now);

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
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(
                () -> new ReservationNotFoundException(reservationId));

        if (reservation.getStatus().equals("cancelled") || reservation.getStatus().equals("completed")) {
            return reservation.getTotalPrice();
        }

        ParkingLocation parkingLocation = parkingLocationService
                .getParkingLocationById(reservation.getParkingLocationId());

        if (parkingLocation == null) {
            throw new ParkingLocationNotFoundException(reservation.getParkingLocationId());
        }

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
        String now = LocalDateTime.now().format(formatter);
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(
                () -> new ReservationNotFoundException(reservationId));

        double cost = getCurrentPrice(reservationId);
        if (reservation != null) {
            if (cost > 0) {
                reservation.setTotalPrice(cost);
                PaymentRequest paymentRequest = new PaymentRequest();
                paymentRequest.reservationId = reservation.getId();
                paymentRequest.isCard = false;
                paymentRequest.status = "waiting";
                paymentRequest.amount = cost;
                paymentService.makePayment(paymentRequest);
            }
            reservation.setStatus("cancelled");
            reservation.setUpdatedAt(now);
            reservationRepository.save(reservation);
        }
        return reservation;
    }

    public Reservation completeReservation(Long reservationId) {
        String now = LocalDateTime.now().format(formatter);
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(
                () -> new ReservationNotFoundException(reservationId));
        ParkingLocation parkingLocation = parkingLocationService
                .getParkingLocationById(reservation.getParkingLocationId());

        if (parkingLocation == null) {
            throw new ParkingLocationNotFoundException(reservation.getParkingLocationId());
        }

        if (reservation != null) {
            reservation.setTotalPrice(getCurrentPrice(reservationId));
            reservation.setStatus("completed");
            reservation.setEndTime(LocalDateTime.now().toString().split("\\.")[0]);
            reservation.setUpdatedAt(now);
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
