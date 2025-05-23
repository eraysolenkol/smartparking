package com.example.smartparking.service;

import org.springframework.stereotype.Service;
import com.example.smartparking.dto.PaymentRequest;
import com.example.smartparking.model.Payment;
import com.example.smartparking.repository.PaymentRepository;
import com.example.smartparking.repository.ReservationRepository;
import com.example.smartparking.model.Reservation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.List;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final ReservationRepository reservationRepository;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    public PaymentService(PaymentRepository paymentRepository,
            ReservationRepository reservationRepository) {
        this.paymentRepository = paymentRepository;
        this.reservationRepository = reservationRepository;
    }

    public Payment makePayment(PaymentRequest paymentRequest) {
        String now = LocalDateTime.now().format(formatter);
        Payment payment = new Payment();
        Reservation reservation = reservationRepository.findById(paymentRequest.reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        payment.setReservation(reservation);
        payment.setAmount(reservation.getTotalPrice());
        payment.setIsCard(paymentRequest.isCard);
        payment.setStatus(paymentRequest.status);
        payment.setCreatedAt(now);
        payment.setUpdatedAt(now);

        return paymentRepository.save(payment);
    }

    public List<Payment> getPaymentsByReservationId(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
        return paymentRepository.findByReservation(reservation);
    }

    public Optional<Payment> getPaymentById(Long paymentId) {
        return paymentRepository.findById(paymentId);
    }

    public List<Payment> getPayments() {
        return paymentRepository.findAll();
    }

    public void deletePayment(Long paymentId) {
        paymentRepository.deleteById(paymentId);
    }

    public void updatePayment(Long paymentId, PaymentRequest paymentRequest, Long reservationId) {
        String now = LocalDateTime.now().format(formatter);
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        payment.setReservation(reservation);
        payment.setAmount(paymentRequest.amount);
        payment.setIsCard(paymentRequest.isCard);
        payment.setStatus(paymentRequest.status);
        payment.setUpdatedAt(now);

        paymentRepository.save(payment);
    }

    public Payment doPayment(Long paymentId, Boolean isCard) {
        String now = LocalDateTime.now().format(formatter);
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        payment.setIsCard(isCard);
        payment.setStatus("Paid");
        payment.setUpdatedAt(now);

        return paymentRepository.save(payment);
    }
}