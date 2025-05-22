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
import org.springframework.web.bind.annotation.DeleteMapping;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    public List<Payment> getPayments() {
        return paymentService.getPayments();
    }

    @GetMapping("/reservation/{reservationId}")
    public List<Payment> getPaymentsByReservationId(@PathVariable Long reservationId) {
        return paymentService.getPaymentsByReservationId(reservationId);
    }

    @GetMapping("/{id}")
    public Optional<Payment> getPaymentById(@PathVariable Long id) {
        return paymentService.getPaymentById(id);
    }

    @PostMapping
    public Payment makePayment(@RequestBody PaymentRequest paymentRequest) {
        return paymentService.makePayment(paymentRequest);
    }

    @PutMapping("/{id}/reservation/{reservationId}")
    public void updatePayment(@PathVariable Long id, @RequestBody PaymentRequest paymentRequest,
            @PathVariable Long reservationId,
            @RequestBody PaymentRequest reservationRequest) {
        paymentService.updatePayment(id, paymentRequest, reservationId);
    }

    @DeleteMapping("/{id}")
    public void deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
    }

}
