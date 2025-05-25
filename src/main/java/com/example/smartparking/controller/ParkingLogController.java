package com.example.smartparking.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.smartparking.service.ParkingLogService;
import com.example.smartparking.model.ParkingLog;
import java.util.List;

@RestController
@RequestMapping("/api/logs")
public class ParkingLogController {

    private final ParkingLogService parkingLogService;

    public ParkingLogController(ParkingLogService parkingLogService) {
        this.parkingLogService = parkingLogService;
    }

    @GetMapping
    public ResponseEntity<List<ParkingLog>> getLogs() {
        List<ParkingLog> logs = parkingLogService.getLogs();
        return ResponseEntity.ok(logs);
    }

}
