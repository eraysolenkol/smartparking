package com.example.smartparking.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.smartparking.service.PaymentService;
import com.example.smartparking.model.Payment;
import java.util.List;
import java.util.Optional;
import com.example.smartparking.dto.PaymentRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    public ResponseEntity<List<Payment>> getPayments() {
        List<Payment> payments = paymentService.getPayments();
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/reservation/{reservationId}")
    public ResponseEntity<List<Payment>> getPaymentsByReservationId(@PathVariable Long reservationId) {
        List<Payment> payments = paymentService.getPaymentsByReservationId(reservationId);
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Long id) {
        Payment payment = paymentService.getPaymentById(id);
        return ResponseEntity.ok(payment);
    }

    @PostMapping
    public ResponseEntity<Payment> makePayment(@RequestBody PaymentRequest paymentRequest) {
        Payment payment = paymentService.makePayment(paymentRequest);
        return new ResponseEntity<>(payment, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/reservation/{reservationId}")
    public ResponseEntity<Payment> updatePayment(@PathVariable Long id, @RequestBody PaymentRequest paymentRequest,
            @PathVariable Long reservationId) {
        Payment payment = paymentService.updatePayment(id, paymentRequest, reservationId);
        return ResponseEntity.ok(payment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Optional<Payment>> deletePayment(@PathVariable Long id) {
        Optional<Payment> payment = paymentService.deletePayment(id);
        return ResponseEntity.ok(payment);
    }

    @PutMapping("/{id}/pay/{isCard}")
    public ResponseEntity<Payment> doPayment(@PathVariable Long id, @PathVariable Boolean isCard) {
        Payment payment = paymentService.doPayment(id, isCard);
        return ResponseEntity.ok(payment);
    }

}
