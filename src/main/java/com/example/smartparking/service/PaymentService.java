package com.example.smartparking.service;

import org.springframework.stereotype.Service;
import com.example.smartparking.dto.PaymentRequest;
import com.example.smartparking.exception.PaymentNotFoundException;
import com.example.smartparking.exception.ReservationNotFoundException;
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
                .orElseThrow(() -> new ReservationNotFoundException(paymentRequest.reservationId));

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
                .orElseThrow(() -> new ReservationNotFoundException(reservationId));
        return paymentRepository.findByReservation(reservation);
    }

    public Payment getPaymentById(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException(paymentId));
        return payment;
    }

    public List<Payment> getPayments() {
        return paymentRepository.findAll();
    }

    public Optional<Payment> deletePayment(Long paymentId) {
        if (!paymentRepository.existsById(paymentId)) {
            throw new PaymentNotFoundException(paymentId);
        }
        Optional<Payment> payment = paymentRepository.findById(paymentId);
        paymentRepository.deleteById(paymentId);
        return payment;
    }

    public Payment updatePayment(Long paymentId, PaymentRequest paymentRequest, Long reservationId) {
        String now = LocalDateTime.now().format(formatter);
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException(paymentId));
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationNotFoundException(reservationId));

        payment.setReservation(reservation);
        payment.setAmount(paymentRequest.amount);
        payment.setIsCard(paymentRequest.isCard);
        payment.setStatus(paymentRequest.status);
        payment.setUpdatedAt(now);

        paymentRepository.save(payment);
        return payment;
    }

    public Payment doPayment(Long paymentId, Boolean isCard) {
        String now = LocalDateTime.now().format(formatter);
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException(paymentId));

        payment.setIsCard(isCard);
        payment.setStatus("Paid");
        payment.setUpdatedAt(now);
        paymentRepository.save(payment);
        return payment;
    }

    public List<Payment> getDailyPayments() {
        String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        List<Payment> payments = paymentRepository.findByCreatedAtStartingWith(today);
        if (payments.isEmpty()) {
            throw new PaymentNotFoundException();
        }
        return payments;
    }

}
