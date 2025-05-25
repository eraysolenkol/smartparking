package com.example.smartparking.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.smartparking.service.ParkingLogService;
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
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentService paymentService;
    private final ParkingLogService parkingLogService;

    public PaymentController(PaymentService paymentService, ParkingLogService parkingLogService) {
        this.paymentService = paymentService;
        this.parkingLogService = parkingLogService;
    }

    @GetMapping
    public ResponseEntity<List<Payment>> getPayments(HttpServletRequest request) {
        String ipAddress = request.getRemoteAddr();
        parkingLogService.log("GET", ipAddress, "Payment", "/api/payments");
        List<Payment> payments = paymentService.getPayments();
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/reservation/{reservationId}")
    public ResponseEntity<List<Payment>> getPaymentsByReservationId(HttpServletRequest request,
            @PathVariable Long reservationId) {
        List<Payment> payments = paymentService.getPaymentsByReservationId(reservationId);
        String ipAddress = request.getRemoteAddr();
        parkingLogService.log("GET", ipAddress, "Payment", "/api/payments/reservation/" + reservationId);
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(HttpServletRequest request, @PathVariable Long id) {
        Payment payment = paymentService.getPaymentById(id);
        String ipAddress = request.getRemoteAddr();
        parkingLogService.log("GET", ipAddress, "Payment", "/api/payments/" + id);
        return ResponseEntity.ok(payment);
    }

    @PostMapping
    public ResponseEntity<Payment> makePayment(HttpServletRequest request, @RequestBody PaymentRequest paymentRequest) {
        Payment payment = paymentService.makePayment(paymentRequest);
        String ipAddress = request.getRemoteAddr();
        parkingLogService.log("POST", ipAddress, "Payment", "/api/payments");
        return new ResponseEntity<>(payment, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/reservation/{reservationId}")
    public ResponseEntity<Payment> updatePayment(HttpServletRequest request, @PathVariable Long id,
            @RequestBody PaymentRequest paymentRequest,
            @PathVariable Long reservationId) {
        Payment payment = paymentService.updatePayment(id, paymentRequest, reservationId);
        String ipAddress = request.getRemoteAddr();
        parkingLogService.log("PUT", ipAddress, "Payment", "/api/payments/" + id + "/reservation/" + reservationId);
        return ResponseEntity.ok(payment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Optional<Payment>> deletePayment(HttpServletRequest request, @PathVariable Long id) {
        Optional<Payment> payment = paymentService.deletePayment(id);
        String ipAddress = request.getRemoteAddr();
        parkingLogService.log("DELETE", ipAddress, "Payment", "/api/payments/" + id);
        return ResponseEntity.ok(payment);
    }

    @PutMapping("/{id}/pay/{isCard}")
    public ResponseEntity<Payment> doPayment(HttpServletRequest request, @PathVariable Long id,
            @PathVariable Boolean isCard) {
        Payment payment = paymentService.doPayment(id, isCard);
        String ipAddress = request.getRemoteAddr();
        parkingLogService.log("PUT", ipAddress, "Payment", "/api/payments/" + id + "/pay/" + isCard);
        return ResponseEntity.ok(payment);
    }

    @GetMapping("/daily-payments")
    public ResponseEntity<List<Payment>> getDailyPayments(HttpServletRequest request) {
        List<Payment> dailyPayments = paymentService.getDailyPayments();
        String ipAddress = request.getRemoteAddr();
        parkingLogService.log("GET", ipAddress, "Payment", "/api/payments/daily-payments");
        return ResponseEntity.ok(dailyPayments);
    }

}
