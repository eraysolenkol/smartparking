package com.example.smartparking.service;

import org.springframework.stereotype.Service;
import com.example.smartparking.dto.PaymentRequest;
import com.example.smartparking.model.Payment;
import com.example.smartparking.repository.PaymentRepository;
import com.example.smartparking.repository.ReservationRepository;
import com.example.smartparking.model.Reservation;
import java.util.Optional;
import java.util.List;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final ReservationRepository reservationRepository;

    public PaymentService(PaymentRepository paymentRepository,
            ReservationRepository reservationRepository) {
        this.paymentRepository = paymentRepository;
        this.reservationRepository = reservationRepository;
    }

    public Payment makePayment(PaymentRequest paymentRequest) {
        Payment payment = new Payment();
        Reservation reservation = reservationRepository.findById(paymentRequest.reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        payment.setReservation(reservation);
        payment.setAmount(reservation.getTotalPrice());
        payment.setIsCard(paymentRequest.isCard);
        payment.setStatus(paymentRequest.status);

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
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        payment.setReservation(reservation);
        payment.setAmount(paymentRequest.amount);
        payment.setIsCard(paymentRequest.isCard);
        payment.setStatus(paymentRequest.status);

        paymentRepository.save(payment);
    }
}