package com.example.smartparking.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import com.example.smartparking.service.ParkingLocationService;
import com.example.smartparking.service.ParkingLogService;
import com.example.smartparking.dto.ParkingLocationRequest;
import com.example.smartparking.model.ParkingLocation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/parking-locations")
public class ParkingLocationController {

    private final ParkingLocationService parkingLocationService;
    private final ParkingLogService parkingLogService;

    public ParkingLocationController(ParkingLocationService parkingLocationService,
            ParkingLogService parkingLogService) {
        this.parkingLocationService = parkingLocationService;
        this.parkingLogService = parkingLogService;
    }

    @GetMapping
    public ResponseEntity<List<ParkingLocation>> getParkingLocations(HttpServletRequest request) {
        List<ParkingLocation> parkingLocations = parkingLocationService.getParkingLocations();
        String ipAddress = request.getRemoteAddr();
        parkingLogService.log("GET", ipAddress, "ParkingLocation", "/api/parking-locations");
        return ResponseEntity.ok(parkingLocations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParkingLocation> getParkingLocationById(HttpServletRequest request, @PathVariable Long id) {
        ParkingLocation parkingLocation = parkingLocationService.getParkingLocationById(id);
        String ipAddress = request.getRemoteAddr();
        parkingLogService.log("GET", ipAddress, "ParkingLocation", "/api/parking-locations/" + id);
        return ResponseEntity.ok(parkingLocation);
    }

    @PostMapping
    public ResponseEntity<ParkingLocation> createParkingLocation(HttpServletRequest httpRequest,
            @RequestBody ParkingLocationRequest request) {
        ParkingLocation parkingLocation = parkingLocationService.createParkingLocation(request);
        String ipAddress = httpRequest.getRemoteAddr();
        parkingLogService.log("POST", ipAddress, "ParkingLocation", "/api/parking-locations");
        return new ResponseEntity<>(parkingLocation, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParkingLocation> updateParkingLocation(HttpServletRequest httpRequest, @PathVariable Long id,
            @RequestBody ParkingLocationRequest request) {
        ParkingLocation parkingLocation = parkingLocationService.updateParkingLocation(id, request);
        String ipAddress = httpRequest.getRemoteAddr();
        parkingLogService.log("PUT", ipAddress, "ParkingLocation", "/api/parking-locations/" + id);
        return ResponseEntity.ok(parkingLocation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ParkingLocation> deleteParkingLocation(HttpServletRequest request, @PathVariable Long id) {
        ParkingLocation parkingLocation = parkingLocationService.deleteParkingLocation(id);
        String ipAddress = request.getRemoteAddr();
        parkingLogService.log("DELETE", ipAddress, "ParkingLocation", "/api/parking-locations/" + id);
        return ResponseEntity.ok(parkingLocation);
    }

}
